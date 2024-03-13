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

  @targetName("append") final override def ++(operand: ThisT): ThisT = fromArray(arrayUnsafe ++ operand.arrayUnsafe)

  def appendInitArray(opArray: Array[Double]): Array[Double] =
  { val deltaLen: Int = (opArray.length - elemProdSize).max0
    val newLen = arrayLen + deltaLen
    val newArray = new Array[Double](newLen)
    arrayUnsafe.copyToArray(newArray)
    Array.copy(opArray, 0, newArray, arrayLen, deltaLen )
    newArray
  }

  @targetName("appendInit") override def ++-(operand: ThisT): ThisT = fromArray(appendInitArray(operand.arrayUnsafe))

  @targetName("appendInitToPolygon") final override  def |++-|(operand: ThisT): PolygonT = polygonFromArray(appendInitArray(operand.arrayUnsafe))

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