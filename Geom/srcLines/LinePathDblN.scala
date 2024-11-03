/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._

trait LinePathDblN[VT <: DblNElem] extends  Any with LinePathLike[VT] with SeqSpecDblN[VT]
{ type ThisT <: LinePathDblN[VT]
  type PolygonT <: PolygonLikeDblN[VT]

  /** Constructs a [[PolygonLike]] for this vertex type from an [[Array]][Double]. */
  def polygonFromArray(array: Array[Double]): PolygonT

  override def tail: ThisT =
  { val newLen = (numVerts - 1).max0
    val newArrayLen = newLen * elemProdSize
    val newArray = new Array[Double](newArrayLen)
    val res = fromArray(newArray)
    var i = 0
    while(i < newLen)
    { res.setElemUnsafe(i, ssIndex(i + 1))
      i += 1
    }
    res
  }

  override def init: ThisT =
  { val newLen = (numVerts - 1).max0
    val newArrayLen = newLen * elemProdSize
    val newArray = new Array[Double](newArrayLen)
    val res = fromArray(newArray)
    var i = 0
    while(i < newLen)
    { res.setElemUnsafe(i, ssIndex(i))
      i += 1
    }
    res
  }

  override def inner: ThisT =
  { val newArrayLen = (numVerts - 2).max0 * elemProdSize
    val newArray = new Array[Double](newArrayLen)
    val res = fromArray(newArray)
    var i = 0
    ssInnerForeach{el => res.setElemUnsafe(i, el); i += 1}
    res
  }

  /** Appends the operand [[LinePathDblN]] of the same type, to this line path returning a new line path of the same type. */
  @targetName("append") final override def ++(operand: ThisT): ThisT = fromArray(arrayUnsafe ++ operand.arrayUnsafe)

  /** Implementation helper method for implementation of appendTail and appendTailToPolygon methods. End users should rarely need to use this, but it's been
   * left public for when it is. */
  def appendTailArray(opArray: Array[Double]): Array[Double] =
  { val deltaLen: Int = (opArray.length - elemProdSize).max0
    val newLen = arrayLen + deltaLen
    val newArray = new Array[Double](newLen)
    arrayUnsafe.copyToArray(newArray)
    Array.copy(opArray, elemProdSize, newArray, arrayLen, deltaLen )
    newArray
  }

  def initAppendInitArray(opArray: Array[Double]): Array[Double] =
  { val init1Len: Int = (arrayUnsafe.length - elemProdSize).max0
    val init2Len: Int = (opArray.length - elemProdSize).max0
    val newLen = init1Len + init2Len
    val newArray = new Array[Double](newLen)
    arrayUnsafe.copyToArray(newArray, 0, init1Len)
    Array.copy(opArray, 0, newArray, init1Len, init2Len)
    newArray
  }

  /** Appends the tail (without its first point of the operand [[LinePathDblN]] of the same type to this line path. The ++ indicates to append a sequence. The
   * trailing - indicates to drop the first point of the operand. */
  @targetName("appendTail") override def ++-(operand: ThisT): ThisT = fromArray(appendTailArray(operand.arrayUnsafe))

  /** Appends the tail (without its first point) of the operand [[LinePathDblN]] of the same type, to this line path closing it off to become a [[PolygonDblN]]
   * of the matching type. The ++ indicates to append a sequence. The leading - indicates to drop the first point of the operand and the enclosing '|'
   * characters indicate to close the line path into a polygon. */
  @targetName("appendTailToPolygon") final override def |++-|(operand: ThisT): PolygonT = polygonFromArray(appendTailArray(operand.arrayUnsafe))

  /** Appends the init (without its first point) of the operand [[LinePathDblN]] of the same type, to the init of this line path closing it off to become a
   * [[PolygonDblN]] of the matching type. The ++ inidcates to append a sequence. The leading - indicates to take the init of this line path. The trailing -
   * indicates to take the init of the operand. The enclosing '|' characters indicate to close the line path into a polygon. */
  @targetName("initAppendInitToPolygon") final override def |-++-|(operand: ThisT): PolygonT = polygonFromArray(initAppendInitArray(operand.arrayUnsafe))

  @targetName("appendVert") @inline final override def +%[AA >: VT](op: VT): ThisT =
  { val newArray = new Array[Double](arrayLen + elemProdSize)
    arrayUnsafe.copyToArray(newArray)
    var i = arrayLen
    op.dblForeach { d =>
      newArray(i) = d
      i += 1
    }
    fromArray(newArray)
  }

  @targetName("appendToPolygon") final override def |++|(operand: ThisT): PolygonT = polygonFromArray(arrayUnsafe ++ operand.arrayUnsafe)

  @targetName("appendVertToPolygon") final override def |+|[AA >: VT](op: VT): PolygonT =
  { val newArray = new Array[Double](arrayLen + elemProdSize)
    arrayUnsafe.copyToArray(newArray)
    var i = arrayLen
    op.dblForeach { d =>
      newArray(i) = d
      i += 1
    }
    polygonFromArray(newArray)
  }

  @targetName("appendReverse") final override def ++<(operand: ThisT): ThisT =
  { val newArray = new Array[Double](arrayLen + operand.arrayLen)
    arrayUnsafe.copyToArray(newArray)
    val res = fromArray(newArray)
    var i = ssLength
    operand.ssReverseForeach{vt =>
      res.setElemUnsafe(i, vt)
      i += 1
    }
    res
  }

  override def toPolygon: PolygonT = polygonFromArray(arrayUnsafe)

  @targetName("appendReverseToPolygon") final override def |++<|(operand: ThisT): PolygonT =
    polygonFromArray((this ++< operand).arrayUnsafe)

  @targetName("prependVert") @inline final override def %: (operand: VT): ThisT =
  { val newArray = new Array[Double](arrayLen + elemProdSize)
    Array.copy(arrayUnsafe, 0, newArray, elemProdSize, arrayLen)
    var i = 0
    operand.dblForeach{d => newArray(i) = d; i += 1 }
    fromArray(newArray)
  }
}

trait LinePathDbl2[VT <: Dbl2Elem] extends Any with LinePathDblN[VT] with SeqSpecDbl2[VT]
{ type ThisT <: LinePathDbl2[VT]
  type PolygonT <: PolygonLikeDbl2[VT]
}

trait LinePathDbl3[VT <: Dbl3Elem] extends Any with LinePathDblN[VT] with SeqSpecDbl3[VT]
{ type ThisT <: LinePathDbl3[VT]
  type PolygonT <: PolygonLikeDbl3[VT]
}