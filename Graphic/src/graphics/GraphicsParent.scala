package ostrat
package geom

/** This is an active visual canvas object. A pointable polygon / shape with visual, that also knows how much display space it needs and preferred
 *  margin space. Not sure about the name. not sure if the trait is useful. */
trait GraphicParent extends GraphicElem with GraphicActive
{ def cen: Vec2
  def elems: Refs[PaintElem]
  def topLeft: GraphicParent = this.slate(- boundingRect.topLeft)
  def topRight: GraphicParent = this.slate(- boundingRect.topRight)
  def bottomLeft: GraphicParent = this.slate(- boundingRect.bottomLeft)
  def bottomRight: GraphicParent = this.slate(- boundingRect.bottomRight)

  def addElems(newElems: Refs[PaintElem]): GraphicParent
  def addElem(newElem: PaintElem): GraphicParent = addElems(Refs(newElem))
  def mutObj(newObj: Any): GraphicParent
}