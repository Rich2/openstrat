/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFx
import javafx.application.Application, javafx.{scene, stage}, javafx.scene.canvas

class AppStart extends Application
{
  override def start(primaryStage: stage.Stage): Unit =
  {
    val bounds = stage.Screen.getPrimary.getVisualBounds
    val canvWidth = findDevSettingElse("displayWidth", bounds.getWidth - 8)
    val canvHeight = findDevSettingElse("displayHeight", bounds.getHeight - 40)
    val canvasCanvas: canvas.Canvas = new canvas.Canvas(canvWidth, canvHeight) 
    val root = new scene.Group()
    root.getChildren.add(canvasCanvas)    
    primaryStage.setX(findDevSettingElse("displayX", 0))//Sets default x value
    primaryStage.setY(findDevSettingElse("displayY", 0))//Should set y value but is not working on Linux
    val jScene = new scene.Scene(root, canvWidth, canvHeight)

    val sett = findDevSetting[Int]("appNum")
    val pair = pDev.Apps.curr(sett.getElse(pDev.Apps.appNum))
    //jScene.setOnKeyReleased{(e: javafx.scene.input.KeyEvent) => deb("Pressed") }
    val newAlt = CanvasFx(canvasCanvas, jScene)
    val myCanv = pair._1(newAlt)
    //jScene.setOnKeyReleased{(e: javafx.scene.input.KeyEvent) => deb("Pressed") }
    primaryStage.setTitle(pair._2)
    primaryStage.setScene(jScene)
    primaryStage.show
  }
}
 
   

