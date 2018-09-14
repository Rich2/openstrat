/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** The base trait for all objects that can have mouse / touch pad interaction. */
trait GraphicActive
{
   /** If the user clicks with the polygon or shape then the canvas will return this object. It is purely up to the application its
    *  response if any to this object */
   def evObj: AnyRef
   /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polyon / shape */
   def boundingRect: BoundingRect
   /** The definitive test as to whether the mouse pointer is inside the polygon / shape */
   def ptInside(pt: Vec2): Boolean
}

object GraphicActive
{
   implicit class GraphicActiveListImplicit(thisList: List[GraphicActive])
   {
      /** Note the lack of reverse at the end */
      def ptInList(pt: Vec2): List[AnyRef] = thisList.filter(subj => subj.boundingRect.ptInside(pt) & subj.ptInside(pt)).map(_.evObj)      
   }
}

/** A pointable polygon */
trait PolyActive extends GraphicActive
{
   def poly: Vec2s
   override def boundingRect = poly.boundingRect
   override def ptInside(pt: Vec2): Boolean = poly.ptInPolygon(pt)
}

/** A pointable shape */
trait ClickShapeTr extends GraphicActive
{
   def shape: Shape
   def innerPoly: Vec2s = shape.pMap(_.pEnd)
   def boundingRect: BoundingRect = innerPoly.boundingRect
   /** This method needs improving */
   override def ptInside(pt: Vec2): Boolean = innerPoly.ptInPolygon(pt)
}

/** A pointable polygon without visual */
case class PolyActiveOnly(poly: Vec2s, evObj: AnyRef) extends GraphicElem[PolyActiveOnly] with PolyActive
{ override def fTrans(f: Vec2 => Vec2) = PolyActiveOnly(poly.fTrans(f), evObj) }

/** A pointable shape without visual */
case class ClickShape(shape: Shape, evObj: AnyRef) extends GraphicElem[ClickShape] with ClickShapeTr
{ override def fTrans(f: Vec2 => Vec2) = ClickShape(shape.fTrans(f), evObj) }

