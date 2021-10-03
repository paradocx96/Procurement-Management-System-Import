// Import React stuff
import React from 'react';
import {BrowserRouter as Router, Redirect, Switch, Route} from 'react-router-dom';

// Import Style
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';

// Import Components
import Login from "./components/pages/Login";
import HeaderBar from "./components/layouts/Header/HeaderBar";


// Dashboard
import Dashboard from "./components/pages/Dashboard";
import DashboardSiteManager from "./components/pages/DashboardSiteManager";
import DashboardSeniorManager from "./components/pages/DashboardSeniorManager";


//Order


//Site
import AddSite from "./components/sections/Site/AddSite";
import Site from "./components/pages/Site";
import ViewAllSites from "./components/sections/Site/ViewAllSites";


//Inventory
import AddCountableItem from "./components/sections/Inventory/CountableItems/AddCountableItem";
import ViewAllCountableItems from "./components/sections/Inventory/CountableItems/ViewAllCountableItems";
import ConsumeCountableItems from "./components/sections/Inventory/CountableItems/ConsumeCountableItems";
import ReplenishCountableItems from "./components/sections/Inventory/CountableItems/ReplenishCountableItems";
import AddUncountableItem from "./components/sections/Inventory/UncountableItems/AddUncountableItem";
import ViewAllUncountableItems from "./components/sections/Inventory/UncountableItems/ViewAllUncountableItems";
import ConsumeUncountableItems from "./components/sections/Inventory/UncountableItems/ConsumeUncountableItems";
import ReplenishUncountableItems from "./components/sections/Inventory/UncountableItems/ReplenishUncountableItems";


//Project
import AddProject from "./components/sections/Project/AddProject";
import ViewAllProject from "./components/sections/Project/ViewAllProject";


//Order
import AddOrder from "./components/sections/Order/AddOrder";
import ViewAllOrder from "./components/sections/Order/ViewAllOrder";



function App() {
    return (
        <div>
            <Router>
                <HeaderBar/>
                <Switch>
                    {/* USER MANAGEMENT */}
                    <Route exact path="/" component={Login}/>


                    {/* DASHBOARD */}
                    <Route path="/dashboard/admin" component={Dashboard}/>
                    <Route path="/dashboard/site" component={DashboardSiteManager}/>
                    <Route path="/dashboard/senior" component={DashboardSeniorManager}/>


                    {/* SITE */}
                    <Route path={'/site'} exact component={Site}/>
                    <Route path="/site/addSite" component={AddSite}/>
                    <Route path="/site/viewAll" component={ViewAllSites}/>

                    {/* INVENTORY */}
                    {/*inventory*/}

                    {/*countable Items*/}
                    <Route path="/inventory/countable/addItem" component={AddCountableItem}/>
                    <Route path="/inventory/countable/viewAll" component={ViewAllCountableItems}/>
                    <Route path="/inventory/countable/consume/:id" component={ConsumeCountableItems}/>
                    <Route path="/inventory/countable/replenish/:id" component={ReplenishCountableItems}/>

                    {/*uncountable items*/}
                    <Route path="/inventory/uncountable/addItem" component={AddUncountableItem}/>
                    <Route path="/inventory/uncountable/viewAll" component={ViewAllUncountableItems}/>
                    <Route path="/inventory/uncountable/consume/:id" component={ConsumeUncountableItems}/>
                    <Route path="/inventory/uncountable/replenish/:id" component={ReplenishUncountableItems}/>


                    {/* PROJECT */}
                    <Route path={'/project/addProject'} exact component={AddProject}/>
                    <Route path={'/project/list'} exact component={ViewAllProject}/>


                    {/* ORDER */}
                    <Route path={'/order/addOrder'} exact component={AddOrder}/>
                    <Route path={'/order/list'} exact component={ViewAllOrder}/>


                    {/* DEFAULT PATH */}
                    <Redirect to="/"/>
                </Switch>
            </Router>
        </div>
    );
}

export default App;
