/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pFx
import pjvm._, javafx.scene._, javafx.stage._, canvas._

object App3D
{
  def main(args: Array[String]): Unit = javafx.application.Application.launch(classOf[AppStart3D], args: _*)
}

class AppStart3D extends javafx.application.Application
{
  override def start(primaryStage: Stage): Unit =
  {
    deb("Hi")
    val bounds = Screen.getPrimary.getVisualBounds
    val canvWidth: Double = findDevSettingElse("displayWidth", bounds.getWidth - 8)
    val canvHeight = findDevSettingElse("displayHeight", bounds.getHeight - 40)
    val canvasCanvas: Canvas = new Canvas(canvWidth, canvHeight)
    val root = new Group()
    
    primaryStage.setX(findDevSettingElse("displayX", 0))//Sets default x value
    primaryStage.setY(findDevSettingElse("displayY", 0))//Should set y value but is not working on Linux
    val camera = new PerspectiveCamera
    camera.translateZProperty.set(-1000)
    val sphere = new shape.Sphere(150)
    root.getChildren.add(sphere)
    val jScene = new Scene(root, canvWidth, canvHeight)
    jScene.setCamera(camera)
    val sett = findDevSettingT[String]("appStr")
    primaryStage.setTitle("3D App")
    primaryStage.setScene(jScene)
    primaryStage.show
  }
}