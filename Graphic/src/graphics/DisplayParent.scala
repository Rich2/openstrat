package ostrat
package geom

trait DisplayParent extends DisplayElem
{ type SimerT <: DisplayParent
  def cen: Vec2
  def boundingRect: BoundingRect
  /** The type of children can probably be widened in the future. */
  def children: Arr[GraphicElem]

  def topLeft: SimerT = this.slate(- boundingRect.topLeft).asInstanceOf[SimerT]
  def topRight: SimerT = this.slate(- boundingRect.topRight).asInstanceOf[SimerT]
  def bottomLeft: SimerT = this.slate(- boundingRect.bottomLeft).asInstanceOf[SimerT] 
  def bottomRight: SimerT = this.slate(- boundingRect.bottomRight).asInstanceOf[SimerT]

  def addElems(newElems: Arr[GraphicElem]): SimerT
  def addElem(newElem: GraphicElem): SimerT = addElems(Arr(newElem))
  def mutObj(newObj: Any): SimerT
}

/** This is an active visual canvas object. A pointable polygon / shape with visual, that also knows how much display space it needs and preferred
 *  margin space. Not sure about the name. not sure if the trait is useful. */
trait DisplayParentFull extends DisplayAffineElem with DisplayActiveFull
{ override type ThisT <: DisplayParentFull
  def cen: Vec2

  /** The type of children can probably be widened in the future. */
  def children: Arr[GraphicFullElem]
  
  def topLeft: ThisT = this.slate(- boundingRect.topLeft)
  def topRight: ThisT = this.slate(- boundingRect.topRight)
  def bottomLeft: ThisT = this.slate(- boundingRect.bottomLeft)
  def bottomRight: ThisT = this.slate(- boundingRect.bottomRight)

  def addElems(newElems: Arr[GraphicFullElem]): ThisT
  def addElem(newElem: GraphicFullElem): ThisT = addElems(Arr(newElem))
  def mutObj(newObj: Any): ThisT
}