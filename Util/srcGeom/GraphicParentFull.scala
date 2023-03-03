/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** This is an active visual canvas object. A pointable polygon / shape with visual, that also knows how much display space it needs and preferred
 *  margin space. Not sure about the name. not sure if the trait is useful. */
trait GraphicParentFull extends GraphicAffineElem with GraphicActiveSim with GraphicBoundedAffine//GraphicActiveAffine
{ override type ThisT <: GraphicParentFull
  def cen: Pt2

  /** The type of children can probably be widened in the future. */
  def children: RArr[GraphicAffineElem]

  def addElems(newElems: RArr[GraphicAffineElem]): ThisT
  def addElem(newElem: GraphicAffineElem): ThisT = addElems(RArr(newElem))
  def mutObj(newObj: AnyRef): ThisT
}