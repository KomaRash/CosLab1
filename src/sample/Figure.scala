package sample

import sample.Kmeans.Point

class Figure(var color: Int) {



  private var _list = List[(Int, Int)]()
   var vectorKmeans = new VectorKmeans
  def kmeans():Figure={
    vectorKmeans.square=list.length.toDouble
    val s=list.groupBy(_._1).toList
    vectorKmeans.perimetr=2*  s.length + s.head._2.length+ s.reverse.head._2.length -4
    vectorKmeans.el(s)

    this
  }

  def equals(point:Point): Boolean ={

    if(vectorKmeans.elongation ==point.elongation && point.comp ==vectorKmeans.comp)
      true
    else false
  }
  def list = _list

  def list_(newValue: List[(Int, Int)]) = {
    _list = (newValue)
    /*
  square
   */
  }

  def add(newValue: (Int,Int)): Figure = {
    this._list = _list ++ List(newValue)
      this
      }
  def add(newValue: List[(Int,Int)]): Figure = {
    this._list = _list ++ newValue
    this
  }




}

class VectorKmeans {
  def comp =_perimetr*_perimetr/_square
  private var _comp = 0.0
  private var _square = 0.0
  private var _perimetr = 0.0
  private var _elongation = 0.0

  def square: Double = _square

  def perimetr: Double = _perimetr

  def elongation: Double = _elongation

  def square_=(double: Double): Unit = _square = double

  def perimetr_=(double: Double): Unit = {
    _perimetr = double
  }

  def el(s: List[(Int,List[(Int, Int)])]): Unit = {
    val sq = s.flatMap(_._2)
    val Mx = sq.map(_._1).sum / sq.length
    val My = sq.map(_._2).sum / sq.length
    val xM=Moment(sq.grouped(s.length).toList,(2,0),(Mx,My))
    val yM=Moment(sq.grouped(s.length).toList,(0,2),(Mx,My))
    val M=Moment(sq.grouped(s.length).toList,(1,1),(Mx,My))
    _elongation=xM+yM+Math.sqrt(Math.pow(xM-yM , 2)+M*M)
    _elongation=_elongation /(xM+yM-Math.sqrt(Math.pow(xM-yM , 2)+M*M))
  }

  def Moment(s: List[List[(Int, Int)]], p: (Int, Int), center: (Int, Int)) = (for (y <- s; x <- y) yield {
    Math.pow(x._1 - center._1, p._1) * Math.pow(x._2 - center._2, p._2)
  }).sum
}
