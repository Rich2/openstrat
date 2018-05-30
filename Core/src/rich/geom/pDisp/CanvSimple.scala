/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom
package pDisp

/** A simple use of the canvas with out splitting it up into Panels */
trait CanvSimple extends PanelLike with CanvUser
{      
   override def width = canv.width
   override def height = canv.height
   
   canv.mouseUp = (v, b) =>
      { 
         val s1 = subjs.flatMap(_.apply(v)).reverse         
         mouseUp(v, b, s1 )
      }
   def refresh(): Unit =
   {
      subjs = Nil
      canv.clear(backColour)
      paintObjs(canvObjs, this)  
   }
   def repaints(els: CanvObj[_]*): Unit = { canvObjs = els; refresh() }   
   def repaint(els: Seq[CanvObj[_]]): Unit = { canvObjs = els; refresh() }   
}