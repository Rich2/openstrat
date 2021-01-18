/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pParse

/** The base trait for all tokens that begin with a digit. For the moment this trait merely serves as a form of documentation. These include natural
 * integers, negative natural integers, raw hexadecimals, raw base32 natural numbers, 0x hexadecimals, 0z base32 natural integers. Decimal fractional,
 * megative decimal fractional, floating point numbers, negative floating point numbers and multipoint sequences such as IP addresses. */
trait DigitalToken extends ExprToken

/** The base trait for all integer tokens. A Natural (non negative) number Token. It contains a single property, the digitStr. The digitStr depending
* on the class may be interpreted in 1 to 3 ways, as a normal decimal number, a hexadecimal number, or a trigdual (base 32) number. */
trait NatToken extends ExprToken
{
  def digitsStr: String
}