/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._

/** A simple use of the canvas with out splitting it up into Panels */
trait CanvasSimple extends PanelLike with CanvUser
{      
   override def width = canv.width
   override def height = canv.height
   
   canv.mouseUp = (v, b) => mouseUp(v, b, subjs.ptInList(v))
      
   def refresh(): Unit =
   {
      canv.clear(backColour)
      subjs = paintObjs(canvObjs)  
   }
   def repaints(els: GraphicElem[_]*): Unit = { canvObjs = els.toList; refresh() }   
   def repaint(els: List[GraphicElem[_]]): Unit = { canvObjs = els; refresh() }   
}

abstract class CanvasTitled(val title: String) extends CanvasSimple