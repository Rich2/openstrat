/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFx
import scalafx.Includes._, scalafx.application.JFXApp//, scalafx.scene._ 

class RSApp(f: javafx.scene.canvas.Canvas => Unit, str: String) extends JFXApp
{   
   stage = new RStage(f, str)  
}

/** N needs tidying up. the x value needs to be a developer setting. */
case class RStage(fDisp: javafx.scene.canvas.Canvas => Unit, tStr: String) extends JFXApp.PrimaryStage  
{  
  x = settingFromFileElse('xOffset, "Dev/GeneralDevSettings.rson", 0)//Sets default x value
  //y = 400// settingFromFileElse('yOffset, "Dev/GeneralDevSettings.rson", -200)//Sets default y value
  class GuiDispFx extends scalafx.scene.Scene
  {
    val bounds = javafx.stage.Screen.getPrimary.getVisualBounds  
    val canvasCanvas: javafx.scene.canvas.Canvas = new javafx.scene.canvas.Canvas(bounds.width - 8, bounds.height - 40)      
    content = canvasCanvas 
    fDisp(canvasCanvas)
  }
  
  val scene1: GuiDispFx = new GuiDispFx         
  title = "ScalaFx" :- tStr
  scene = scene1   
}
   
   

