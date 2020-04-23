package ostrat
package geom

trait GraphicsParent extends GraphicElem

/** This is an active visual canvas object. A pointable polygon / shape with visual, that also knows how much display space it needs and preferred
 *  margin space. Not sure about the name. not sure if the trait is useful. */
trait GraphicParentFull extends GraphicFullElem with GraphicActiveFull
{ override type ThisT <: GraphicParentFull
  def cen: Vec2

  /** The type of children can probably be widened in the future. */
  def children: Arr[PaintFullElem]
  
  def topLeft: ThisT = this.slate(- boundingRect.topLeft)
  def topRight: ThisT = this.slate(- boundingRect.topRight)
  def bottomLeft: ThisT = this.slate(- boundingRect.bottomLeft)
  def bottomRight: ThisT = this.slate(- boundingRect.bottomRight)

  def addElems(newElems: Arr[PaintFullElem]): ThisT
  def addElem(newElem: PaintFullElem): ThisT = addElems(Arr(newElem))
  def mutObj(newObj: Any): ThisT
}