package tricera.postprocessor

import ap.parser._
import ap.terfor.ConstantTerm
import IExpression._
import ap.types.MonoSortedIFunction

object ACSLExpression {
  val valid = new Predicate("\\valid", 1)
  val deref = new IFunction("deref", 1, false, false) // *p
  val oldDeref = new IFunction("oldDeref", 1, false, false) // \old(*p)
  val derefOldPointer =
    new IFunction("derefOldPointer", 1, false, false) // *\old(p)
  val arrow = new IFunction("arrow", 2, false, false) // p->a
  val arrowOldPointer =
    new IFunction("arrowOldPointer", 2, false, false) // \old(p)->a
  val oldArrow = new IFunction("oldArrow", 2, false, false) // \old(p)->a

  // Here a ConstantTerm is introduced as a container for the variable name
  def derefFunApp(
      derefFunction: IFunction,
      pointer: ISortedVariable,
      quantifierDepth: Int,
      cci: ContractConditionInfo
  ) = {
    val name = cci.stripOld(cci.getVarName(pointer, quantifierDepth))
    IFunApp(derefFunction, Seq(IConstant(new ConstantTerm(name))))
  }

  def arrowFunApp(
      arrowFunction: IFunction,
      pointer: ISortedVariable,
      selector: MonoSortedIFunction,
      quantifierDepth: Int,
      cci: ContractConditionInfo
  ) = {
    val pointerName = cci.stripOld(
      cci.getVarName(pointer, quantifierDepth)
    )
    val selectorName = selector.name
    IFunApp(
      arrowFunction,
      Seq(
        IConstant(new ConstantTerm(pointerName)),
        IConstant(new ConstantTerm(selectorName))
      )
    )
  }
}