package ostrat
package geom

trait GraphicParent extends GraphicElem
{override type RigidT <: GraphicParent
  def cen: Vec2
  def boundingRect: BoundingRect
  /** The type of children can probably be widened in the future. */
  def children: Arr[PaintElem]

  def topLeft: RigidT = this.slate(- boundingRect.topLeft)
  def topRight: RigidT = this.slate(- boundingRect.topRight)
  def bottomLeft: RigidT = this.slate(- boundingRect.bottomLeft)
  def bottomRight: RigidT = this.slate(- boundingRect.bottomRight)

  def addElems(newElems: Arr[PaintElem]): RigidT
  def addElem(newElem: PaintElem): RigidT = addElems(Arr(newElem))
  def mutObj(newObj: Any): RigidT
}

/** This is an active visual canvas object. A pointable polygon / shape with visual, that also knows how much display space it needs and preferred
 *  margin space. Not sure about the name. not sure if the trait is useful. */
trait GraphicParentFull extends GraphicFullElem with GraphicActiveFull
{ override type RigidT <: GraphicParentFull
  def cen: Vec2

  /** The type of children can probably be widened in the future. */
  def children: Arr[PaintFullElem]
  
  def topLeft: RigidT = this.slate(- boundingRect.topLeft)
  def topRight: RigidT = this.slate(- boundingRect.topRight)
  def bottomLeft: RigidT = this.slate(- boundingRect.bottomLeft)
  def bottomRight: RigidT = this.slate(- boundingRect.bottomRight)

  def addElems(newElems: Arr[PaintFullElem]): RigidT
  def addElem(newElem: PaintFullElem): RigidT = addElems(Arr(newElem))
  def mutObj(newObj: Any): RigidT
}