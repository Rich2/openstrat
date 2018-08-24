/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** This is an active visual canvas object. A pointable polygon / shape with visual, that also knows how much dispaly space it needs and preferred 
 *  margin space. Not sure about the name. not sure if the trait is useful. */
trait GraphicSubject[A <: GraphicSubject[A]] extends GraphicElem[A] with GraphicActive
{
   def cen: Vec2
   def elems: List[PaintElem[_]]  
   def tL: A = slate(boundingRect.bR)
   def tR: A = slate(boundingRect.bL)
   def bL: A = slate(boundingRect.tR)
   def bR: A = slate(boundingRect.tL)
   def width = boundingRect.width    
   def addElems(newElems: List[PaintElem[_]]): A
   def addElem(newElem: PaintElem[_]): A = addElems(List(newElem))
   def mutObj(newObj: AnyRef): A
}
