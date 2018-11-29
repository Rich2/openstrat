/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait GraphicActiveT[T]
{ /** If the user clicks with the polygon or shape then the canvas will return this object. It is purely up to the application its
   *  response if any to this object */
  def evObj: T
  /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polyon / shape */
  def boundingRect: BoundingRect
  /** The definitive test as to whether the mouse pointer is inside the polygon / shape */
  def ptInside(pt: Vec2): Boolean
}

object GraphicActiveT
{
  implicit class GraphicActiveTListImplicit[T](thisList: List[GraphicActiveT[T]])
  { /** Note the lack of reverse at the end */
    def ptInList(pt: Vec2): List[T] = thisList.filter(subj => subj.boundingRect.ptInside(pt) & subj.ptInside(pt)).map(_.evObj)      
  }
}