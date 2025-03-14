# Command Examples

### RAD - Prism invocation
     
    python3 ULTIMATE_numerical_solver.py \
    --path "path to prism" \
    --mc "Prism" \
    --model "select_perception_model.dtmc" \
    --input "select_perception_model.dtmc, perceive-user2.dtmc, P=? [F (\"done\" & (userOk & userPredictedOk))], pOkCorrect" "select_perception_model.dtmc, perceive-user2.dtmc, P=? [F (\"done\" & (!(userOk) & !(userPredictedOk)))], pNotOkCorrect" "perceive-user2.dtmc, select_perception_model.dtmc, P=?[F s=1], pModel1" "perceive-user2.dtmc, select_perception_model.dtmc, P=?[F s=2], pModel2"


### RAD - Storm invocation (runs much faster than Prism)
    python3 ULTIMATE_numerical_solver.py \
    --path "storm" \
    --mc "Storm" \
    --model "select_perception_model.dtmc" \
    --input "select_perception_model.dtmc, perceive-user2.dtmc, P=? [F (\"done\" & (userOk & userPredictedOk))], pOkCorrect" "select_perception_model.dtmc, perceive-user2.dtmc, P=? [F (\"done\" & (!(userOk) & !(userPredictedOk)))], pNotOkCorrect" "perceive-user2.dtmc, select_perception_model.dtmc, P=?[F s=1], pModel1" "perceive-user2.dtmc, select_perception_model.dtmc, P=?[F s=2], pModel2"


### Command Line Arguments
    
    --path: the path to Prism/Storm 
    --mc:  the selected probabilistic model checker
    --model: the model within the SCC to start the optimisation from
    --input: the set of dependency parameters within SCC models
    

Each input has the structure:
     
1) dependent_model
2) source_model
3) property
4) variable name in dependent model
