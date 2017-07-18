<p align="center">

<img src="https://user-images.githubusercontent.com/1777595/28303339-57071e98-6b9b-11e7-89a4-845ad22e04ce.png" alt="CircularPicker" title="CircularPicker" width="500"/>
</p>

<p>

<p align="center">

<a href="https://www.agilie.com?utm_source=github&utm_medium=referral&utm_campaign=Git_Swift&utm_term=AGVolumeControlView">
<img src="https://img.shields.io/badge/Made%20by-Agilie-green.svg?style=flat" alt="Made by Agilie">
</a>

<a href='https://bintray.com/agilie/maven/VolumeControlView/_latestVersion'>
<img src='https://api.bintray.com/packages/agilie/maven/VolumeControlView/images/download.svg'>
</a>

<a href='https://raw.githubusercontent.com/agilie/VolumeControlView/master/LICENSE.txt'>
<img src='https://img.shields.io/badge/license-MIT-blue.svg' alt='GitHub license'>
</a>

</p>

We’re happy to introduce you a new free regulator VolumeControlView based on our lightweight open-source visual component that doesn't require extra lines of code and can be easily integrated into your project. Visual regulator can be connected to a player or other smart house’s device making the process of controlling the level of a particular characteristic much easier.

### Demo

<img src="https://user-images.githubusercontent.com/17047537/26981465-ef1759ae-4d3d-11e7-85bd-b04338761719.gif"> <img src="https://user-images.githubusercontent.com/17047537/26981461-edd0ee7a-4d3d-11e7-8b79-5f9ce4a80552.gif"> 

## Link to iOS repo

Check out our iOS [VolumeControlView](https://github.com/agilie/AGVolumeControlView) also!

## Example
To run the example project, clone the repo, and run sample.
### How to use

Just add VolumeControlView to your layout file:
```kotlin
 <com.agilie.volumecontrol.view.VolumeControlView
        android:id="@+id/controllerView"
        android:layout_width="wrap_content"
        android:layout_height="270dp">
````

The visual display of this regulator can be easily customized. One has a possibility to choose colors, the gradient style and background according to the wishes:
```kotlin
var colors : intArrayOf
var backgroundLayoutColor : Color
var minShiningRadius : Float
var maxShiningRadius : Float
var shiningFrequency : Float
````
````xml
<declare-styleable name="VolumeControlView">
        <attr name="innerCircleColor" format="color" />
        <attr name="movableCircleColor" format="color" />
        <attr name="splineCircleColor" format="color" />
        <attr name="controllerSpace" format="float" />
        <attr name="sectorRadius" format="integer" />
        <attr name="movableCircleRadius" format="float" />
        <attr name="minShiningRadius" format="float" />
        <attr name="maxShiningRadius" format="float" />
        <attr name="shiningFrequency" format="float" />
</declare-styleable>
````
## Usage

### Gradle

Add dependency in your `build.gradle` file:
````gradle
compile 'com.agilie:volume-control-view:1.0'
````

### Maven
Add  dependency in your `.pom` file:
````xml
<dependency>
  <groupId>com.agilie</groupId>
  <artifactId>volume-control-view</artifactId>
  <version>1.0</version>
  <type>pom</type>
</dependency>
````

## Requirements

VolumeControlView works on Android API 19+

## Troubleshooting

Problems? Check the [Issues](https://github.com/agilie/VolumeControlView/issues) block
to find the solution or create an new issue that we will fix asap.

## Author

This library is open-sourced by [Agilie Team](https://www.agilie.com?utm_source=github&utm_medium=referral&utm_campaign=Git_Android_Kotlin&utm_term=VolumeControlView) <info@agilie.com>

## Contributors

- [Eugene Surkov](https://github.com/ukevgen)

## Contact us
If you have any questions, suggestions or just need a help with web or mobile development, please email us at <br />
<android@agilie.com> <br />
You can ask us anything from basic to complex questions. <br />
We will continue publishing new open-source projects. Stay with us, more updates will follow! <br />

## License

The [MIT](LICENSE.md) License (MIT) Copyright © 2017 [Agilie Team](https://www.agilie.com?utm_source=github&utm_medium=referral&utm_campaign=Git_Android_Kotlin&utm_term=VolumeControlView)
