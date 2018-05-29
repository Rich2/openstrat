/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package pSJs
import org.scalajs.dom._
import scala.scalajs.js.Any.fromString

object WebGlApp //extends scala.scalajs.js.JSApp
{
   def main(args: Array[String]): Unit =  
   {
       val can: html.Canvas = document.createElement("canvas").asInstanceOf[html.Canvas]      
       document.body.appendChild(can)      
       val width = window.innerWidth -20
       val height = (window.innerHeight - 80)      
       can.width = width.toInt
       can.height = height.toInt
       can.asInstanceOf[scalajs.js.Dynamic].style = "border:2px solid black;"
       val dec = new raw.CSSStyleDeclaration()
       val gc = can.getContext("2d").asInstanceOf[raw.CanvasRenderingContext2D]
     //  can. .setProperty("borderColor", Colour.blue.hexStr)
       gc.fillStyle = "#AA5500"
       gc.fillRect(0, 0, 200, 200)       
       dec.borderWidth = "2"
       can.style = dec
       
       can.dyn.style = "border:2px solid black;"      
  //     can.rectFillBL(20, 20, 400, 200, Colour.burlywood)
//      canv .rectFillBL(220, 220, 400, 200, Colour.red)
      //triangles(
//         Seq[Vec2]( Vec2(-0.3f,-0.3f),  Vec2(0.3f,-0.3f),  Vec2(0.0f,0.3f), 
//          Vec2(-0.3f,0.3f),  Vec2(0.3f, 0.3f),  Vec2(0.0f,0.9f)),
//          Colour.olive)
//      val cards = Card.newPack.shuffle
//      for (i <- 0 to 3)
//      {
//         cards(i).canvDispTL(canv, i * 200, 0)
//      }
 
   }
}