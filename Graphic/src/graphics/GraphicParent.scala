package ostrat
package geom

trait GraphicParent extends GraphicElem
{override type AlignT <: GraphicParent
  def cen: Vec2
  def boundingRect: BoundingRect
  /** The type of children can probably be widened in the future. */
  def children: Arr[PaintElem]

  def topLeft: AlignT = this.slate(- boundingRect.topLeft)
  def topRight: AlignT = this.slate(- boundingRect.topRight)
  def bottomLeft: AlignT = this.slate(- boundingRect.bottomLeft)
  def bottomRight: AlignT = this.slate(- boundingRect.bottomRight)

  def addElems(newElems: Arr[PaintElem]): AlignT
  def addElem(newElem: PaintElem): AlignT = addElems(Arr(newElem))
  def mutObj(newObj: Any): AlignT
}

/** This is an active visual canvas object. A pointable polygon / shape with visual, that also knows how much display space it needs and preferred
 *  margin space. Not sure about the name. not sure if the trait is useful. */
trait GraphicParentFull extends GraphicFullElem with GraphicActiveFull
{ override type AlignT <: GraphicParentFull
  def cen: Vec2

  /** The type of children can probably be widened in the future. */
  def children: Arr[PaintFullElem]
  
  def topLeft: AlignT = this.slate(- boundingRect.topLeft)
  def topRight: AlignT = this.slate(- boundingRect.topRight)
  def bottomLeft: AlignT = this.slate(- boundingRect.bottomLeft)
  def bottomRight: AlignT = this.slate(- boundingRect.bottomRight)

  def addElems(newElems: Arr[PaintFullElem]): AlignT
  def addElem(newElem: PaintFullElem): AlignT = addElems(Arr(newElem))
  def mutObj(newObj: Any): AlignT
}