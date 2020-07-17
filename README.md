#  自定义View库

收录整理常用的各种UI控件，自定义view


[![](https://jitpack.io/v/itxiaox/view.svg)](https://jitpack.io/#itxiaox/view)



## 使用

1. 引用

在根gradle中添加

   
```
allprojects {
    	repositories {
    		...
    		maven { url 'https://jitpack.io' }
    	}
    }
```

	
2.在module 中添加依赖


```
//万能apater适配器，适配ListView/RecycleView
implementation 'com.github.itxiaox.view:adapter:TAG'
//常用对话框
implementation 'com.github.itxiaox.view:dialog:TAG'
//进度条
implementation 'com.github.itxiaox.view:progressbar:TAG'
//地址选择器
implementation 'com.github.itxiaox.view:address:TAG'
//日期、时间、数据选择器
implementation 'com.github.itxiaox.view:picker:TAG'
//菜单
implementation 'com.github.itxiaox.view:menu:TAG'
//常见的自定义view
implementation 'com.github.itxiaox.view:xview:TAG'

```


 
# LICENSE

	Copyright 2018 Xiaox

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.

 

