/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** Hex tile representing an island or a headland and its surrounding waters or similarly geometrically structured terrain where the main land area of
 *  the tile is surrounded by water on a number of tiles. */
trait HOuter
{ def numIndentedSides: Int
}

/** Hex tile representing an island and its surrounding waters or similarly geometrically structured terrain where the main land area of the tile is
 *  surrounded by water on all 6 sides. */
trait HOuter6 extends HOuter
{ override def numIndentedSides: Int = 6
}

trait HOuter5Minus extends HOuter
{ /** Hex sides are numbered from 0 to 5 in a clockwise direction starting at the top right. */
  def indentStartIndex:Int
}

/** Hex tile representing a headland and its surrounding waters or similarly geometrically structured terrain where the main land area of the tile is
 *  surrounded by water on 5 sides. */
trait HOuter5 extends HOuter5Minus
{ override def numIndentedSides: Int = 5
}

/** Hex tile representing a headland and its surrounding waters or similarly geometrically structured terrain where the main land area of the tile is
 *  surrounded by water on 4 sides. */
trait HOuter4 extends HOuter5Minus
{ override def numIndentedSides: Int = 4
}

/** Hex tile representing a headland and its surrounding waters or similarly geometrically structured terrain where the main land area of the tile is
 *  surrounded by water on 3 sides. */
trait HOuter3 extends HOuter5Minus
{ override def numIndentedSides: Int = 3
}

/** Hex tile representing a headland and its surrounding waters or similarly geometrically structured terrain where the main land area of the tile is
 *  surrounded by water on 2 sides. */
trait HOuter2 extends HOuter5Minus
{ override def numIndentedSides: Int = 2
}

trait HSideGeom
{
  def nonEmpty: Boolean = true
}

trait HSideNone extends HSideGeom
{
  override def nonEmpty: Boolean = false
}

trait HSideSome extends HSideGeom

trait HSideMid extends HSideSome

/** A hex side that has an indent on an [[Houter]] tile to its left. */
trait HSideLeft extends HSideSome

/** A hex side that has an indent on an [[Houter]] tile to its right. */
trait HSideRight extends HSideSome

/** A hex side that has indents on [[Houter]] tiles to its left and Right. */
trait HSideLeftRight extends HSideSome