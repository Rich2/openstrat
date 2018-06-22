/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDisp
import geom._

/** A simple use of the canvas with out splitting it up into Panels */
trait CanvasSimple extends PanelLike with CanvUser
{      
   override def width = canv.width
   override def height = canv.height
   
   canv.mouseUp = (v, b) => mouseUp(v, b, subjs.ptInList(v))
      
   def refresh(): Unit =
   {
      subjs = Nil
      canv.clear(backColour)
      paintObjs(canvObjs, this)  
   }
   def repaints(els: CanvObj[_]*): Unit = { canvObjs = els; refresh() }   
   def repaint(els: Seq[CanvObj[_]]): Unit = { canvObjs = els; refresh() }   
}