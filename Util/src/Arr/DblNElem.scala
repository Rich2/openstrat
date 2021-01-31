/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** An object that can be constructed from N [[Double]]s. These are used as elements in [[DblNsArr]] Array[Double] based collections. */
trait DblNElem extends Any with ValueNElem
{ //def defaultDelta: Double = 1e-12
}

/** An object that can be constructed from a single [[Double]]. These are used in [[Dbl1sArr]] Array[Int] based collections. */
trait Dbl1Elem extends Any with DblNElem
{ def dbl1: Double
  def dblsEqual(that: Dbl1Elem): Boolean = dbl1 == that.dbl1
}

/** An object that can be constructed from 2 [[Double]]s. These are used as elements in [[Dbl2sArr]] Array[Double] based collections. */
trait Dbl2Elem extends Any with DblNElem
{ def dbl1: Double
  def dbl2: Double
  def dblsEqual(that: Dbl2Elem): Boolean = dbl1 == that.dbl1 & dbl2 == that.dbl2
  def dblsApprox(that: Dbl2Elem, delta: Double = 1e-12): Boolean = dbl1.=~(that.dbl1, delta) & dbl2.=~(that.dbl2, delta)
}

/** An object that can be constructed from 3 [[Double]]s. These are used in [[Dbl3sArr]] Array[Double] based collections. */
trait Dbl3Elem extends Any with DblNElem
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dblsEqual(that: Dbl3Elem): Boolean = dbl1 == that.dbl1 & dbl2 == that.dbl2 & dbl3 == that.dbl3

  def dblsApprox(that: Dbl3Elem, delta: Double = 1e-12): Boolean =
    dbl1.=~(that.dbl1, delta) & dbl2.=~(that.dbl2, delta) & dbl3.=~(that.dbl3, delta)
}

/** An object that can be constructed from 4 [[Double]]s. These are used in [[Dbl4sArr]] Array[Double] based collections. */
trait Dbl4Elem extends Any with ValueNElem
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double
}

/** An object that can be constructed from 5 [[Double]]s. These are used in [[Dbl5sArr]] Array[Double] based collections. */
trait Dbl5Elem extends Any with ValueNElem
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double
  def dbl5: Double
}

/** An object that can be constructed from 6 [[Double]]s. These are used in [[Dbl6sArr]] Array[Double] based collections. */
trait Dbl6Elem extends Any with ValueNElem
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double
  def dbl5: Double
  def dbl6: Double
}

/** An object that can be constructed from 7 [[Double]]s. These are used in [[Dbl7sArr]] Array[Double] based collections. */
trait Dbl7Elem extends Any with ValueNElem
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double
  def dbl5: Double
  def dbl6: Double
  def dbl7: Double
}