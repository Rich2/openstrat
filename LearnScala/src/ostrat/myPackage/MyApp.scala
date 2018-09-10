package ostrat
package myPackage
import scalafx._, Includes._ , application.JFXApp, scene._
//import pFx._

object MyApp extends scalafx.application.JFXApp 
{
  stage = new JFXApp.PrimaryStage
  {
     x = 0//1920
     class GuiDispFx extends scalafx.scene.Scene
     {
         val bounds = javafx.stage.Screen.getPrimary.getVisualBounds  
         val canvasCanvas: canvas.Canvas = new canvas.Canvas(bounds.width - 8, bounds.height - 40)      
         content = canvasCanvas 
        // Lesson1(CanvasFx(canvasCanvas))
      }
      val scene1: GuiDispFx = new GuiDispFx         
      title = "Lesson1"
     scene = scene1
  }
}