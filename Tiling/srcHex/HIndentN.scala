/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** Hex tile indented on a number of its vertices, representing an island or a headland and its surrounding waters or similarly geometrically
 *  structured terrain where the main land area of the tile is surrounded by water on a number of tiles. */
trait HIndentN
{ def numIndentedSides: Int
}

/** Hex tile indented on 6 of its sides, representing an island and its surrounding waters or similarly geometrically structured terrain where the
 *  main land area of the tile is surrounded by water on all 6 sides. */
trait HIndent6 extends HIndentN
{ override def numIndentedSides: Int = 6
}

/** Hex tile indented on 5 or less of its vertices, representing a headland and its surrounding waters or similarly geometrically structured terrain#
 *  where the main land area of the tile is surrounded by water on a number of its sides */
trait HIndent5Minus extends HIndentN
{ /** Hex sides are numbered from 0 to 5 in a clockwise direction starting at the top right. This is the index of the first indented vertex. */
  def indentStartIndex:Int
}

/** Hex tile indented on 4 of its vertices, representing a headland and its surrounding waters or similarly geometrically structured terrain where the
 *  main land area of the tile is surrounded by water on 5 sides. */
trait HIndent4 extends HIndent5Minus
{ override def numIndentedSides: Int = 5
}

/** Hex tile indented on 3 vertices, representing a headland and its surrounding waters or similarly geometrically structured terrain where the main
 *  land area of the tile is surrounded by water on 4 sides. */
trait HIndent3 extends HIndent5Minus
{ override def numIndentedSides: Int = 4
}

/** Hex tile indented on 2 vertices, representing a headland and its surrounding waters or similarly geometrically structured terrain where the main
 *  land area of the tile is surrounded by water on 3 sides. */
trait HIndent2 extends HIndent5Minus
{ override def numIndentedSides: Int = 3
}

/** Hex tile indented on 1 vertex representing a headland and its surrounding waters or similarly geometrically structured terrain where the main land
 *  area of the tile is surrounded by water on 2 sides. */
trait HIndent1 extends HIndent5Minus
{ override def numIndentedSides: Int = 2
}

trait HSideGeom
{
  def nonEmpty: Boolean = true
}

trait HSideNone //extends HSideGeom

trait HSideSome //extends HSideGeom

trait HSideMid extends HSideSome
/*
/** A hex side that has an indent on an [[Houter]] tile to its left. */
trait HSideLeft extends HSideSome

/** A hex side that has an indent on an [[Houter]] tile to its right. */
trait HSideRight extends HSideSome

/** A hex side that has indents on [[Houter]] tiles to its left and Right. */
trait HSideLeftRight extends HSideSome*/
