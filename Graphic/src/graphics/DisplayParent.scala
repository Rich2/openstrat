/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait DisplayParent extends DisplayElem
{ type ThisT <: DisplayParent
  def cen: Vec2
  def boundingRect: BoundingRect
  /** The type of children can probably be widened in the future. */
  def children: Arr[GraphicElem]

  def topLeft: ThisT = this.slate(- boundingRect.topLeft).asInstanceOf[ThisT]
  def topRight: ThisT = this.slate(- boundingRect.topRight).asInstanceOf[ThisT]
  def bottomLeft: ThisT = this.slate(- boundingRect.bottomLeft).asInstanceOf[ThisT] 
  def bottomRight: ThisT = this.slate(- boundingRect.bottomRight).asInstanceOf[ThisT]

  def addElems(newElems: Arr[GraphicElem]): ThisT
  def addElem(newElem: GraphicElem): ThisT = addElems(Arr(newElem))
  def mutObj(newObj: Any): ThisT
}

/** This is an active visual canvas object. A pointable polygon / shape with visual, that also knows how much display space it needs and preferred
 *  margin space. Not sure about the name. not sure if the trait is useful. */
trait DisplayParentFull extends DisplayAffineElem with DisplayActiveFull
{ override type ThisT <: DisplayParentFull
  def cen: Vec2

  /** The type of children can probably be widened in the future. */
  def children: Arr[GraphicAffElem]
  
  def topLeft: ThisT = this.slate(- boundingRect.topLeft)
  def topRight: ThisT = this.slate(- boundingRect.topRight)
  def bottomLeft: ThisT = this.slate(- boundingRect.bottomLeft)
  def bottomRight: ThisT = this.slate(- boundingRect.bottomRight)

  def addElems(newElems: Arr[GraphicAffElem]): ThisT
  def addElem(newElem: GraphicAffElem): ThisT = addElems(Arr(newElem))
  def mutObj(newObj: Any): ThisT
}