/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** Hex tile indented on a number of its vertices, representing an island or a headland and its surrounding waters or similarly geometrically
 *  structured terrain where the main land area of the tile is surrounded by water on a number of tiles. */
trait HcIndentN
{ def numIndentedVerts: Int

  /** Hex sides are numbered from 0 to 5 in a clockwise direction starting at the top right. This is the index of the first indented vertex. */
  def indentStartIndex: Int

  def indentedVertexIndexForeach(f: Int => Unit): Unit

  def indentedVertexIndexMap[B, ArrB <: Arr[B]](f: Int => B)(implicit build: BuilderArrMap[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    indentedVertexIndexForeach{ i => buff.grow(f(i)) }
    build.buffToSeqLike(buff)
  }

  def indentedSideIndexForeach(f: Int => Unit): Unit
}

/** Hex tile indented on 6 of its sides, representing an island and its surrounding waters or similarly geometrically structured terrain where the
 *  main land area of the tile is surrounded by water on all 6 sides. */
trait HcIndent6 extends HcIndentN
{ override def numIndentedVerts: Int = 6
  override def indentStartIndex: Int = 0

  override def indentedVertexIndexForeach(f: Int => Unit): Unit = iUntilForeach(6)(f)

  override def indentedSideIndexForeach(f: Int => Unit): Unit = iUntilForeach(6)(f)
}

trait HSepOpt

trait HSepNone extends HSepOpt

/** This trait is purely to tag an object as a something. */
trait HSepSome extends HSepOpt