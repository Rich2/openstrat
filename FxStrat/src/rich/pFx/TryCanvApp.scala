/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich.pFx
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene._
import rich._
object TryCanvApp  extends JFXApp
{
   stage = new JFXApp.PrimaryStage
   {
      scene = new scalafx.scene.Scene
      {
         content = new canvas.Canvas
         {
            val bounds = javafx.stage.Screen.getPrimary.getVisualBounds  
            width = bounds.width
            height = bounds.height -50
            val gc: canvas.GraphicsContext = graphicsContext2D
            val gc1 = gc.save()
            gc.beginPath()
            gc.rect(0, 0 , 1000, height())
            gc.closePath()
            gc.clip()
            gc.fill = Colour.Violet.fx
            gc.fillRect(0, 0, 200, 120)
            gc.fill = Colour.Blue.fx
            gc.fillRect(800, 800, 200, 120)
            gc.fill = Colour.Green.fx
            gc.fillRect(width() - 200, height() - 100, 200, 100)
            gc.restore()
            gc.fill = Colour.Brown.fx
            gc.fillRect(width() - 200, height() - 100, 100, 50)           
         }         
      }
   }
  
}