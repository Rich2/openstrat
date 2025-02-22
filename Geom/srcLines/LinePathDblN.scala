/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
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
    { res.setElemUnsafe(i, index(i + 1))
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
    { res.setElemUnsafe(i, index(i))
      i += 1
    }
    res
  }

  override def inner: ThisT =
  { val newArrayLen = (numVerts - 2).max0 * elemProdSize
    val newArray = new Array[Double](newArrayLen)
    val res = fromArray(newArray)
    var i = 0
    innerForeach{el => res.setElemUnsafe(i, el); i += 1}
    res
  }
  
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

  @targetName("appendTail") override def +-+(operand: ThisT): ThisT = fromArray(appendTailArray(operand.arrayUnsafe))
  @targetName("appendTailToPolygon") final override def |+-+|(operand: ThisT): PolygonT = polygonFromArray(appendTailArray(operand.arrayUnsafe))
  @targetName("initAppendInitToPolygon") final override def |-++-|(operand: ThisT): PolygonT = polygonFromArray(initAppendInitArray(operand.arrayUnsafe))
  @targetName("appendPt") @inline final override def +%(operandPt: VT): ThisT = fromArray(appendPtToArray(operandPt))
  @targetName("appendPtToPolygon") final override def |+%|(operandPt: VT): PolygonT = polygonFromArray(appendPtToArray(operandPt))

  def appendPtToArray(pt: VT): Array[Double] =
  { val newArray = new Array[Double](arrayLen + elemProdSize)
    arrayUnsafe.copyToArray(newArray)
    var i = arrayLen
    pt.dblForeach { d =>
      newArray(i) = d
      i += 1
    }
    newArray
  }

  @targetName("appendToPolygon") final override def |++|(operand: ThisT): PolygonT = polygonFromArray(arrayUnsafe ++ operand.arrayUnsafe)

  @targetName("appendVertToPolygon") final override def |+|[AA >: VT](pt: VT): PolygonT =
  { val newArray = new Array[Double](arrayLen + elemProdSize)
    arrayUnsafe.copyToArray(newArray)
    var i = arrayLen
    pt.dblForeach { d =>
      newArray(i) = d
      i += 1
    }
    polygonFromArray(newArray)
  }

  @targetName("appendReverse") final override def ++<(operand: ThisT): ThisT =
  { val newArray = new Array[Double](arrayLen + operand.arrayLen)
    arrayUnsafe.copyToArray(newArray)
    val res = fromArray(newArray)
    var i = numElems
    operand.reverseForeach{vt =>
      res.setElemUnsafe(i, vt)
      i += 1
    }
    res
  }

  override def toPolygon: PolygonT = polygonFromArray(arrayUnsafe)
  @targetName("appendReverseToPolygon") final override def |++<|(operand: ThisT): PolygonT = polygonFromArray((this ++< operand).arrayUnsafe)

  @targetName("prepend") @inline final override def %: (operand: VT): ThisT =
  { val newArray = new Array[Double](arrayLen + elemProdSize)
    Array.copy(arrayUnsafe, 0, newArray, elemProdSize, arrayLen)
    var i = 0
    operand.dblForeach{d => newArray(i) = d; i += 1 }
    fromArray(newArray)
  }

  @targetName("prependReverse") @inline final override def %<:(operand: VT): ThisT =
  { val newArray = new Array[Double](arrayLen + elemProdSize)
    var i = 0
    operand.dblForeach { d => newArray(i) = d; i += 1 }
    val res = fromArray(newArray)
    i = 1
    reverseForeach { vt =>
      res.setElemUnsafe(i, vt)
      i += 1
    }
    res
  }

  @targetName("reverseAppend") final override def +<+(operand: ThisT): ThisT =
  { val newArray = new Array[Double](arrayLen + operand.arrayLen)
    val res = fromArray(newArray)
    var i = 0
    reverseForeach { vt =>
      res.setElemUnsafe(i, vt)
      i += 1
    }
    Array.copy(operand.arrayUnsafe, 0, newArray, arrayLen, operand.arrayLen)
    res
  }

  @targetName("reverseAppendToPolygon") final override def |+<+|(operand: ThisT): PolygonT =
  { val newArray = new Array[Double](arrayLen + operand.arrayLen)
    val res = polygonFromArray(newArray)
    var i = 0
    reverseForeach { vt =>
      res.setElemUnsafe(i, vt)
      i += 1
    }
    Array.copy(operand.arrayUnsafe, 0, newArray, arrayLen, operand.arrayLen)
    res
  }

  @targetName("reverseAppendReverse") final override def +<+<(operand: ThisT): ThisT =
  { val newArray = new Array[Double](arrayLen + operand.arrayLen)
    val res = fromArray(newArray)
    var i = 0
    reverseForeach { vt =>
      res.setElemUnsafe(i, vt)
      i += 1
    }
    operand.reverseForeach { vt =>
      res.setElemUnsafe(i, vt)
      i += 1
    }
    res
  }
}

/** [[LinePathLike]] whose points are [[Dbl2Elem]]s. Includes the [[LinePath]] class. */
trait LinePathDbl2[VT <: Dbl2Elem] extends Any with LinePathDblN[VT] with SeqSpecDbl2[VT]
{ type ThisT <: LinePathDbl2[VT]
  type PolygonT <: PolygonLikeDbl2[VT]
}

/**[[LinePathLike]] whose points are[[Dbl3Elem]]s. */
trait LinePathDbl3[VT <: Dbl3Elem] extends Any with LinePathDblN[VT] with SeqSpecDbl3[VT]
{ type ThisT <: LinePathDbl3[VT]
  type PolygonT <: PolygonLikeDbl3[VT]
}