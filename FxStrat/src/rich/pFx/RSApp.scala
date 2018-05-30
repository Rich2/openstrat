/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package pFx
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.canvas.{Canvas => CanvasFx}    
class RSApp(f: CanvasFx => Unit, str: String) extends JFXApp
{   
   stage = new RStage(f, str)  
}

class RStage(fDisp: CanvasFx => Unit, tStr: String) extends JFXApp.PrimaryStage  
{
   x = 0//1920
   class GuiDispFx extends scalafx.scene.Scene
   {
      val bounds = javafx.stage.Screen.getPrimary.getVisualBounds  
      val canvFx: CanvasFx = new CanvasFx(bounds.width - 8, bounds.height - 40)         
      //val canv = new CanvDispFx(canvFx)
      content = canvFx 
      fDisp(canvFx)
   }
   val scene1: GuiDispFx = new GuiDispFx         
   title = "ScalaFx" :- tStr
   scene = scene1
   
}
   
   

