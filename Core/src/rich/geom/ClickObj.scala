/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom

/** The base trait for all objects that can have mouse / touch pad interaction */
trait ClickObj
{
   /** If the user clicks with the polygon or shape then the canvas will return this object. It is purely up to the application its
    *  response if any to this object */
   def evObj: AnyRef
   /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polyon / shape */
   def boundingRect: BoundingRect
   /** The definitive test as to whether the mouse pointer is inside the polygon / shape */
   def ptInside(pt: Vec2): Boolean
}

object ClickObj
{
   implicit class ClickObjListImplicit(thisList: List[ClickObj])
   {
      /** Note the lack of reverse at the end */
      def ptInList(pt: Vec2): List[AnyRef] =
      {
         var evObjs: List[AnyRef] = Nil         
         thisList.foreach {subj =>  if (subj.boundingRect.ptInside(pt) & subj.ptInside(pt)) evObjs ::= subj.evObj }        
         evObjs
      }
   }
}

/** A pointable polygon */
trait ClickPolyTr extends ClickObj
{
   def poly: Vec2s
   override def boundingRect = poly.boundingRect
   override def ptInside(pt: Vec2): Boolean = poly.ptInPolygon(pt)
}

/** A pointable shape */
trait ClickShapeTr extends ClickObj
{
   def shape: Seq[ShapeSeg]
   def innerPoly: Vec2s = shape.pMap(_.endPt)
   def boundingRect = innerPoly.boundingRect
   /** This method needs improving */
   override def ptInside(pt: Vec2): Boolean = innerPoly.ptInPolygon(pt)
}

/** A pointable polygon without visual */
case class ClickPoly(poly: Vec2s, evObj: AnyRef) extends CanvObj[ClickPoly] with ClickPolyTr
{ override def fTrans(f: Vec2 => Vec2) = ClickPoly(poly.fTrans(f), evObj) }

/** A pointable shape without visual */
case class ClickShape(shape: Seq[ShapeSeg], evObj: AnyRef) extends CanvObj[ClickShape] with ClickShapeTr
{ override def fTrans(f: Vec2 => Vec2) = ClickShape(shape.fTrans(f), evObj) }

