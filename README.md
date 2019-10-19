<img src="https://github.com/andrewjapar/redapter/blob/master/images/redapter-logo.png" width="250">

[![Release](https://api.bintray.com/packages/andrewjapar/Android/Redapter/images/download.svg)](https://bintray.com/andrewjapar/Android/Redapter/_latestVersion)

:zap: A lazy tool to compose RecyclerView Adapter for Android :zap:

## Installation
### Gradle
```kotlin
apply plugin: 'kotlin-kapt'

repositories {
    jcenter()
}

implementation 'com.andrewjapar.redapter:redapter:0.1.0'
kapt 'com.andrewjapar.redapter:redapter-compiler:0.1.0'
```

## Usage
### 1. Create your data model or view entity
Just simply create a class which implements the `RedapterModel`
#### For single view type
```kotlin
data class UserInfo(val name: String): RedapterModel
```
#### For multi view type
```
sealed class UserInfo: RedapterModel {
    data class Portrait(val image: String): UserInfo()
    data class Landscape(val name: String): UserInfo()
}
```
### 2. Create your view holder
Create a class which implements `Redapter.ViewHolder` and annotate it with `@BindLayout`. They you have to override `onBind` function in order to implement click listener and consume the data model.
```kotlin
@BindLayout(layout = R.layout.item_user_info, model = UserInfo.Portrait::class)
class PortraitUserInfoViewHolder(view: View) : Redapter.ViewHolder(view) {
    override fun onBind(item: RedapterModel, itemActionListener: (RedapterModel) -> Unit) {
    }
}
```

### 3. Implement adapter on activity or fragment
Add `@BindViewHolder` annotation on your adapter variable, just like injecting something using dagger.
```kotlin
class MainActivity : AppCompatActivity() {

    @BindViewHolder(UserInfo.Portrait::class, UserInfo.Landscape::class)
    lateinit var adapter: Redapter.Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Redapter.bind(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      
        adapter.data = mutableListOf(UserInfo.Portrait("url"), UserInfo.Landscape("name"))
    }
}
```

### 4. (Optional) Create your own adapter class
Just in case you want to create your own adapter class, you can use the same annotation `@BindViewHolder` for the adapter class. Try to build the project and extend it with adapter class name with suffix `_Helper`.
```kotlin
@BindViewHolder(UserInfo.Portrait::class, UserInfo.Landscape::class)
class MainAdapter : MainAdapter_Helper()
```


## Future Plan
1. Support View Bindings
2. Pagination
3. Please let me know, if you have any other ideas

## Contributing
Currently, redapter is used only on my personal project but if you have any idea to make it more powerfull, just make a pull request, You are in!

## License
```
    MIT License
    
    Copyright (c) 2019 andrewjapar
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publiAsh, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
