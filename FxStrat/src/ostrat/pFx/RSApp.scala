/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFx
import scalafx.Includes._, scalafx.application.JFXApp, scalafx.scene._ 

class RSApp(f: canvas.Canvas => Unit, str: String) extends JFXApp
{   
   stage = new RStage(f, str)  
}

/** N needs tidying up. the x value needs to be a developer setting. */
case class RStage(fDisp: canvas.Canvas => Unit, tStr: String) extends JFXApp.PrimaryStage  
{ 
  x = 0//1920
  class GuiDispFx extends scalafx.scene.Scene
  {
    val bounds = javafx.stage.Screen.getPrimary.getVisualBounds  
    val canvasCanvas: canvas.Canvas = new canvas.Canvas(bounds.width - 8, bounds.height - 40)      
    content = canvasCanvas 
    fDisp(canvasCanvas)
  }
  
  val scene1: GuiDispFx = new GuiDispFx         
  title = "ScalaFx" :- tStr
  scene = scene1   
}
   
   

