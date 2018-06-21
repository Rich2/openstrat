/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom

/** This is an active visual canvas object. A pointable polyon / shape with visual. Not sure about the name. not sure if the trait is
 *   useful. */
trait CanvSubj[T <: CanvSubj[T]] extends CanvObj[T] with ClickObj
{
   def cen: Vec2
   def elems: Seq[CanvEl[_]]  
   def tL: T = slate(boundingRect.bR)
   def tR: T = slate(boundingRect.bL)
   def bL: T = slate(boundingRect.tR)
   def bR: T = slate(boundingRect.tL)
   def width = boundingRect.width    
   def addElems(newElems: Seq[CanvEl[_]]): T
   def addElem(newElem: CanvEl[_]): T = addElems(Seq(newElem))
   def mutObj(newObj: AnyRef): T
}
