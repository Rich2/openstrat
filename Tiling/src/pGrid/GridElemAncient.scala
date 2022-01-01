/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pGrid

/** In a simple Grid the only GridElems are Tiles. In a complex grid, there are values for tiles and tile sides. For programmatic purposes it is
 *  vital that GridElem contains its grid coordinates, although at a logical level a tile does not need to know its position within the tile grid.
 *  It needs this information so it can communicate with its grid. */
trait GridElemAncient
{ def x: Int
  def y: Int
  def cood: Cood = Cood(x, y)
}

trait TileAncient extends GridElemAncient
{
  type FromT
  def fromT: FromT
  def canEqual(a: Any) = a.isInstanceOf[TileAncient]
  override def hashCode: Int = (x, y).##

  override def equals(that: Any): Boolean = that match
  { case that: TileAncient => that.canEqual(this) & cood == that.cood
    case _ => false
  }
}

trait TileSideAncient extends GridElemAncient
{ def canEqual(a: Any) = a.isInstanceOf[TileSideAncient]
  override def hashCode: Int = (x, y).##
  override def equals(that: Any): Boolean = that match
  { case that: TileSideAncient => that.canEqual(this) & cood == that.cood
    case _ => false
  }
}

trait ColouredTileAncient extends TileAncient with Coloured

case class TileBareAncient(x: Int, y: Int) extends TileAncient
{ type FromT = Unit
  def fromT = ()
}

case class SideBareAncient(x: Int, y: Int) extends TileSideAncient

object SideBareAncient
{ implicit object SideBareIsType extends IsType[SideBareAncient]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[SideBareAncient]
    override def asType(obj: AnyRef): SideBareAncient = obj.asInstanceOf[SideBareAncient]
  }
}

case class TileRow[T <: AnyRef](yRow: Int, xStart: Int, yStart: Int, values: Arr[Multiple[T]])

object TileRow {
  implicit def eqImplicit[T <: AnyRef](implicit ev: EqT[T]): EqT[TileRow[T]] = ??? //EqCase3(_.yRow, _.xStart, _.values)

  implicit def persistImplicit[T <: AnyRef](implicit ev: Persist[T]): Persist[TileRow[T]] = new Persist[TileRow[T]] {
    def strT(obj: TileRow[T]): String = (posnStr() + ": This is a placeholder for TileRow").enquote

    def showComma(obj: TileRow[T]): String = strT(obj)

    def showSemi(obj: TileRow[T]): String = strT(obj)

    def showTyped(obj: TileRow[T]): String = strT(obj)

    def syntaxDepthT(obj: TileRow[T]): Int = 3 //ev.syntaxDepth() + 2

    // def fromClauses(clauses: Refs[ostrat.pParse.Clause]): ostrat.EMon[ostrat.pGrid.TileRow[T]] = ???
    def fromExpr(expr: pParse.Expr): ostrat.EMon[TileRow[T]] = ???

    override def showT(obj: TileRow[T], way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???

    //  def fromStatements(sts: Refs[ostrat.pParse.Statement]): ostrat.EMon[ostrat.pGrid.TileRow[T]] = ???
    def typeStr: String = ???
  }
}