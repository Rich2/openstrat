/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFx
import javafx.application.Application, javafx.scene.Scene, javafx.stage.Stage

class AppStart extends Application
{
  override def start(primaryStage: Stage): Unit = 
  {
    val bounds = javafx.stage.Screen.getPrimary.getVisualBounds
    val canvasCanvas: javafx.scene.canvas.Canvas = new javafx.scene.canvas.Canvas(bounds.getWidth - 8, bounds.getHeight - 40) 
    val root = new javafx.scene.Group()
    root.getChildren.add(canvasCanvas)        
    primaryStage.setX(settingFromFileElse('xOffset, "Dev/GeneralDevSettings.rson", 0))//Sets default x value
    primaryStage.setY(400)//settingFromFileElse('xOffset, "Dev/GeneralDevSettings.rson", 200))
    val jScene = new Scene(root, bounds.getWidth - 8, bounds.getHeight - 40)
    val pair = pDev.Apps.curr//(fromRsonFileFindElse(openStratDir / "Dev/AppNum.txt", 1))
    val newAlt = CanvasFx(canvasCanvas)
    pair._1(newAlt)
    primaryStage.setTitle(pair._2)
    primaryStage.setScene(jScene)
    primaryStage.show
  }
}
 
   

