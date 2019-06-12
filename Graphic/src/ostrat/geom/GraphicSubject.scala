/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** This is an active visual canvas object. A pointable polygon / shape with visual, that also knows how much display space it needs and preferred 
 *  margin space. Not sure about the name. not sure if the trait is useful. */
trait GraphicSubject extends GraphicElem with GraphicActive
{ def cen: Vec2
  def elems: Arr[PaintElem]
  def tL: GraphicSubject = this.slate(boundingRect.bottomRight)
  def tR: GraphicSubject = this.slate(boundingRect.bottomLeft)
  def bL: GraphicSubject = this.slate(boundingRect.topRight)
  def bR: GraphicSubject = this.slate(boundingRect.topLeft)
  def width = boundingRect.width    
  def addElems(newElems: Arr[PaintElem]): GraphicSubject
  def addElem(newElem: PaintElem): GraphicSubject = addElems(Arr(newElem))
  def mutObj(newObj: AnyRef): GraphicSubject
}
