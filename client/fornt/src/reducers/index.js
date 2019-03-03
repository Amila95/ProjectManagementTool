import {combineReducers} from "redux";
import errorReducer from "./errorReduce";
import projectReducer from "./projectReducer";

export default combineReducers({
    errors:errorReducer,
    project:projectReducer
});