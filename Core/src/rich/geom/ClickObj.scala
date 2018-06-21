/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom

case class ClickPoly(poly: Vec2s, evObj: AnyRef) extends CanvObj[ClickPoly] with ClickPolyTr
{
   override def fTrans(f: Vec2 => Vec2) = ClickPoly(poly.fTrans(f), evObj)  
}

case class ClickShape(shape: Seq[ShapeSeg], evObj: AnyRef) extends CanvObj[ClickShape] with ClickShapeTr
{
   //override def ptIn: Vec2 => Boolean = shape.ptInShape
   override def fTrans(f: Vec2 => Vec2) = ClickShape(shape.fTrans(f), evObj)   
}

sealed trait ClickObj
{
   def evObj: AnyRef
   def boundingRect: BoundingRect
}

object ClickObj
{
   implicit class ClickObjListImplicit(thisList: List[ClickObj])
   {
      /** Not the lack of reverse at the end */
      def ptInList(pt: Vec2): List[AnyRef] =
      {
         var evObjs: List[AnyRef] = Nil         
         thisList.foreach {subj =>            
            if (subj.boundingRect.ptInside(pt)) subj match
            {             
               case cp: ClickPolyTr if cp.poly.ptInPolygon(pt) => {deb("Click poly"); evObjs ::= cp.evObj }
               case cs: ClickShapeTr if cs.innerPoly.ptInPolygon(pt) => {deb("ClickShape"); evObjs ::= cs.evObj}
               case cs: ClickShapeTr => deb("ClickShape near")
               case _ =>   
            }
         }
         evObjs
      }
   }
}

trait ClickPolyTr extends ClickObj
{
   def poly: Vec2s
   def boundingRect = poly.boundingRect
}

trait ClickShapeTr extends ClickObj
{
   def shape: Seq[ShapeSeg]
   def innerPoly: Vec2s = shape.pMap(_.endPt)
   def boundingRect = innerPoly.boundingRect
}

/** Currently this trait combines 2 things */
trait CanvSubj[T <: CanvSubj[T]] extends CanvObj[T] with ClickObj
{
   def cen: Vec2
   def elems: Seq[CanvEl[_]]  
   def tL: T = slate(boundingRect.bR)
   def tR: T = slate(boundingRect.bL)
   def bL: T = slate(boundingRect.tR)
   def bR: T = slate(boundingRect.tL)
   def width = boundingRect.width    
   def addElems(newElems: Seq[CanvEl[_]]): T
   def addElem(newElem: CanvEl[_]): T = addElems(Seq(newElem))
   def mutObj(newObj: AnyRef): T
}