/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** This is an active visual canvas object. A pointable polygon / shape with visual. Not sure about the name. not sure if the trait is
 *   useful. */
trait CanvSubj[T <: CanvSubj[T]] extends CanvObj[T] with ClickObj
{
   def cen: Vec2
   def elems: List[CanvEl[_]]  
   def tL: T = slate(boundingRect.bR)
   def tR: T = slate(boundingRect.bL)
   def bL: T = slate(boundingRect.tR)
   def bR: T = slate(boundingRect.tL)
   def width = boundingRect.width    
   def addElems(newElems: List[CanvEl[_]]): T
   def addElem(newElem: CanvEl[_]): T = addElems(List(newElem))
   def mutObj(newObj: AnyRef): T
}
