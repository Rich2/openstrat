/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** This is an active visual canvas object. A pointable polygon / shape with visual, that also knows how much display space it needs and preferred 
 *  margin space. Not sure about the name. not sure if the trait is useful. */
@deprecated trait GraphicParentOld extends GraphicElem with GraphicActive
{ def cen: Vec2
  def elems: ArrOld[PaintElem]
  def tL: GraphicParentOld = this.slate(boundingRect.bottomRight)
  def tR: GraphicParentOld = this.slate(boundingRect.bottomLeft)
  def bL: GraphicParentOld = this.slate(boundingRect.topRight)
  def bR: GraphicParentOld = this.slate(boundingRect.topLeft)
  //def width: Double = boundingRect.width
  def addElems(newElems: ArrOld[PaintElem]): GraphicParentOld
  def addElem(newElem: PaintElem): GraphicParentOld = addElems(ArrOld(newElem))
  def mutObj(newObj: AnyRef): GraphicParentOld
}


