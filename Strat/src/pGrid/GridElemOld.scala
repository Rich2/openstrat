/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

/** In a simple Grid the only GridElems are Tiles. In a complex grid, there are values for tiles and tile sides. For programmatic purposes it is
 *  vital that GridElem contains its grid coordinates, although at a logical level a tile does not need to know its position within the tile grid.
 *  It needs this information so it can communicate with its grid. */
@deprecated trait GridElemOld
{
   def x: Int
   def y: Int
   def cood: Cood = Cood(x, y)
}

@deprecated trait TileOld extends GridElemOld
{
  type FromT
  def fromT: FromT
  def canEqual(a: Any) = a.isInstanceOf[TileOld]
  override def hashCode: Int = (x, y).##
  override def equals(that: Any): Boolean = that match
  { case that: TileOld => that.canEqual(this) & cood == that.cood
    case _ => false
  }

}

@deprecated trait TileSideOld extends GridElemOld
{ def canEqual(a: Any) = a.isInstanceOf[TileSideOld]
  override def hashCode: Int = (x, y).##
  override def equals(that: Any): Boolean = that match
  { case that: TileSideOld => that.canEqual(this) & cood == that.cood
    case _ => false
  }
}

trait ColouredTileOld extends TileOld with WithColour

case class TileOldBare(x: Int, y: Int) extends TileOld
{ type FromT = Unit
  def fromT = ()
}

case class SideOldBare(x: Int, y: Int) extends TileSideOld

object SideOldBare
{ implicit object SideBareIsType extends IsType[SideOldBare]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[SideOldBare]
    override def asType(obj: AnyRef): SideOldBare = obj.asInstanceOf[SideOldBare]
  }
}

case class TileRow[T](yRow: Int, xStart: Int, yStart: Int, values: ArrOld[Multiple[T]])
object TileRow
{
  implicit def eqImplicit[T](implicit ev: Eq[T]): Eq[TileRow[T]] = EqCase3(_.yRow, _.xStart, _.values)

  implicit def persistImplicit[T](implicit ev: Persist[T]): Persist[TileRow[T]] = new Persist[TileRow[T]]
  { def show(obj: ostrat.pGrid.TileRow[T]): String = (deb.str :- "This is a placeholder for TileRow").enquote
    def showComma(obj: ostrat.pGrid.TileRow[T]): String = show(obj)
    def showSemi(obj: ostrat.pGrid.TileRow[T]): String = show(obj)
    def showTyped(obj: ostrat.pGrid.TileRow[T]): String = show(obj)
    def syntaxDepth: Int = ev.syntaxDepth + 2
   // def fromClauses(clauses: Refs[ostrat.pParse.Clause]): ostrat.EMon[ostrat.pGrid.TileRow[T]] = ???
    def fromExpr(expr: pParse.Expr): ostrat.EMon[ostrat.pGrid.TileRow[T]] = ???
  //  def fromStatements(sts: Refs[ostrat.pParse.Statement]): ostrat.EMon[ostrat.pGrid.TileRow[T]] = ???
    def typeStr: String = ???
  }
}