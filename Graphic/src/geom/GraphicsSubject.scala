package ostrat
package geom

/** This is an active visual canvas object. A pointable polygon / shape with visual, that also knows how much display space it needs and preferred
 *  margin space. Not sure about the name. not sure if the trait is useful. */
trait GraphicSubject extends GraphicElem with GraphicActive
{ def cen: Vec2
  def elems: Refs[PaintElem]
  def topLeft: GraphicSubject = this.slate(- boundingRect.topLeft)
  def topRight: GraphicSubject = this.slate(- boundingRect.topRight)
  def bottomLeft: GraphicSubject = this.slate(- boundingRect.bottomLeft)
  def bottomRight: GraphicSubject = this.slate(- boundingRect.bottomRight)
  def width: Double = boundingRect.width
  def addElems(newElems: Refs[PaintElem]): GraphicSubject
  def addElem(newElem: PaintElem): GraphicSubject = addElems(Refs(newElem))
  def mutObj(newObj: AnyRef): GraphicSubject
}


