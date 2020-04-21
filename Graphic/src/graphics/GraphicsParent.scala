package ostrat
package geom

/** This is an active visual canvas object. A pointable polygon / shape with visual, that also knows how much display space it needs and preferred
 *  margin space. Not sure about the name. not sure if the trait is useful. */
trait GraphicParent extends GraphicFullElem with GraphicActive
{ def cen: Vec2

  /** The type of children can probably be widened in the future. */
  def children: Arr[PaintFullElem]
  
  def topLeft: GraphicParent = this.slate(- boundingRect.topLeft)
  def topRight: GraphicParent = this.slate(- boundingRect.topRight)
  def bottomLeft: GraphicParent = this.slate(- boundingRect.bottomLeft)
  def bottomRight: GraphicParent = this.slate(- boundingRect.bottomRight)

  def addElems(newElems: Arr[PaintFullElem]): GraphicParent
  def addElem(newElem: PaintFullElem): GraphicParent = addElems(Arr(newElem))
  def mutObj(newObj: Any): GraphicParent
}