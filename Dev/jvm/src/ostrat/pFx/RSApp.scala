/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFx
import javafx.{scene, stage}, scene.canvas._

class AppStart extends javafx.application.Application
{
  override def start(primaryStage: stage.Stage): Unit =
  {
    val bounds = stage.Screen.getPrimary.getVisualBounds
    val canvWidth: Double = findDevSettingElse("displayWidth", bounds.getWidth - 8)
    val canvHeight = findDevSettingElse("displayHeight", bounds.getHeight - 40)
    val canvasCanvas: Canvas = new Canvas(canvWidth, canvHeight)
    val root = new scene.Group()
    root.getChildren.add(canvasCanvas)
    primaryStage.setX(findDevSettingElse("displayX", 0))//Sets default x value
    primaryStage.setY(findDevSettingElse("displayY", 0))//Should set y value but is not working on Linux
    val jScene = new scene.Scene(root, canvWidth, canvHeight)
    val sett = findDevSetting[String]("appStr")
    val pair = pDev.Apps.curr(sett.getElse(pDev.Apps.appStr))
    val newAlt = CanvasFx(canvasCanvas, jScene)
    pair._1(newAlt)
    primaryStage.setTitle(pair._2)
    primaryStage.setScene(jScene)
    primaryStage.show
  }
}
 
   

