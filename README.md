# VolumeControlView


### VolumeControl

<img src="https://user-images.githubusercontent.com/17047537/26981461-edd0ee7a-4d3d-11e7-8b79-5f9ce4a80552.gif">
   
<img src="https://user-images.githubusercontent.com/17047537/26981465-ef1759ae-4d3d-11e7-85bd-b04338761719.gif">


[Agilie Team](https://agilie.com/en/ios-development-services) would like to offer you our new lightweight open-source library called VolumeControlView.
This library can act as any controller and can be easily integrated into your project.

When can you use AGMobileGift?

Problems? Check the [Issues](https://github.com/agilie/VolumeControlView/issues) block
to find the solution or create an new issue that we will fix asap.

## Example

### How does it work?
To create a controller for your taste, you need to select the background color (the background color must match the color of your layout), the color palette of the controller itself and the color of the glow.

````kotlin

controllerView.backgroundLayoutColor = Color.BLACK
controllerView.colors = intArrayOf()
controllerView.setBackgroundShiningColor(Color.parseColor("#FF7F00"))

````


In VolumeControlerView implemented onTouchControllerListener which contains methods *onControllerDown*, *onControllerMove*, *onAngleChange*.
How can they be used?
For example, the *onAngleChange* method:
````kotlin

override fun onAngleChange(angle: Int, percent: Int) {
                value.setText(percent.toString() + "%")

                when (angle) {
                    in 0..45 -> controllerView.setBackgroundShiningColor(Color.parseColor("#FF7F00"))
                    in 46..90 -> controllerView.setBackgroundShiningColor(Color.parseColor("#9FFF00"))
                    in 91..135 -> controllerView.setBackgroundShiningColor(Color.parseColor("#FACC00"))
                    in 136..180 -> controllerView.setBackgroundShiningColor(Color.parseColor("#3B9800"))
                    in 181..225 -> controllerView.setBackgroundShiningColor(Color.parseColor("#00493D"))
                    in 226..270 -> controllerView.setBackgroundShiningColor(Color.parseColor("#E7FBE1"))
                    in 271..315 -> controllerView.setBackgroundShiningColor(Color.parseColor("#53FFFF"))
                    in 316..360 -> controllerView.setBackgroundShiningColor(Color.parseColor("#FF7F00"))
                }
}
````


Depending on the angle of rotation of the controller, the illumination color changes.

Attributes that can also be managed:
1. Glow frequency
2. Radius of glow
3. The initial position of the controller

```kotlin
controllerView.setStartPercent(50)
controllerView.setShiningMinRadius
controllerView.setShiningMaxRadius
controllerView.setShiningFrequency
````
VolumeControlView parameters that can be specified when adding to your layout file:
```kotlin
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

````

### Maven
Add  dependency in your `.pom` file:
````xml

````

## Requirements

VolumeControlView works on Android API 19+


## Author

This library is open-sourced by [Agilie Team](https://www.agilie.com) <info@agilie.com>

## Contributors

- [Eugene Surkov](https://github.com/ukevgen)

## Contact us
<android@agilie.com>


## License

The [MIT](LICENSE.md) License (MIT) Copyright © 2017 [Agilie Team](https://www.agilie.com)
