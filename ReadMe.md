# ViewModel example

- jetpack 뷰 모델 사용 해보기
- 뷰모델을 사용하기 때문에 화면 회전 등 에도 데이터는 보존됨
    - 프레그먼트 재시작 마다 onActivityCreate() 에서 뷰모델 데이터를 가져옴
  
- 이어서 dataBinding 사용 해보기

## DataBinding 의존성 추가

```groovy
plugins {
  ...
  id 'kotlin-kapt'
}

android {
  ...
  buildFeatures {
    viewBinding true
    dataBinding true
  }
}
```

## Layout 컴포넌트를 root 로 설정 및 data 요소 추가
ex)
main_activity.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="myViewModel"
            type="com.lilcode.example.viewmodeldemo.ui.main.MainViewModel" />
    </data>
...
</layout>
```


## 바인딩 클래스 생성

- 액티비티 클래스에서 바인딩 클래스 생성

```kotlin
lateinit var binding: ActivityMainBinding

binding = DataBindingUtil.inflate(
    inflater, R.layout.activity_main, container, false
)
```

- 프래그먼트 에서 바인딩 클래스 생성

```kotlin
lateinit var binding: MainFragmentBinding

binding = DataBindingUtil.inflate(
  inflater, R.layout.main_fragment, container, false
)

binding.setLifecycleOwner(this)

return binding.root
```

## 데이터 바인딩 변수에 ViewModel 인스턴스 지정 해보기 

```kotlin
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.lifecycleOwner = this // 프레그먼트가 존재할 때만 바인딩이 남아있어야 하기 떄문에 연결. (소멸시 같이 소멸)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.setVariable(myViewModel, viewModel) // 데이터 바인딩 변수에 ViewModel 인스턴스 지정 해보기

          ...
    }

```

## 바인딩 표현식 사용해보기

- 단방향 바인딩
```xml
        <TextView
            ...
            android:text='@{safeUnbox(myViewModel.result) == 0.0f ? "Enter value" : String.valueOf(safeUnbox(myViewModel.result)) + " euros"}'
            ... />
```

- boxing 된 타입의 경우에는 `safeUnbox()` 로 언박싱 하여 기본타입으로 변경해주어야함

- 양방향 바인딩 (need `MutableLiveData`)
```xml
        <EditText
            ...
            android:text="@={myViewModel.dollarValue}"
            ... />
```