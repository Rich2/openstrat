package ostrat
package geom

trait GraphicParent extends GraphicElemOld
{override type SimerT <: GraphicParent
  def cen: Vec2
  def boundingRect: BoundingRect
  /** The type of children can probably be widened in the future. */
  def children: Arr[PaintElemOld]

  def topLeft: SimerT = this.slate(- boundingRect.topLeft)
  def topRight: SimerT = this.slate(- boundingRect.topRight)
  def bottomLeft: SimerT = this.slate(- boundingRect.bottomLeft)
  def bottomRight: SimerT = this.slate(- boundingRect.bottomRight)

  def addElems(newElems: Arr[PaintElemOld]): SimerT
  def addElem(newElem: PaintElemOld): SimerT = addElems(Arr(newElem))
  def mutObj(newObj: Any): SimerT
}

/** This is an active visual canvas object. A pointable polygon / shape with visual, that also knows how much display space it needs and preferred
 *  margin space. Not sure about the name. not sure if the trait is useful. */
trait GraphicParentFull extends GraphicFullElem with GraphicActiveFull
{ override type SimerT <: GraphicParentFull
  def cen: Vec2

  /** The type of children can probably be widened in the future. */
  def children: Arr[PaintFullElem]
  
  def topLeft: SimerT = this.slate(- boundingRect.topLeft)
  def topRight: SimerT = this.slate(- boundingRect.topRight)
  def bottomLeft: SimerT = this.slate(- boundingRect.bottomLeft)
  def bottomRight: SimerT = this.slate(- boundingRect.bottomRight)

  def addElems(newElems: Arr[PaintFullElem]): SimerT
  def addElem(newElem: PaintFullElem): SimerT = addElems(Arr(newElem))
  def mutObj(newObj: Any): SimerT
}