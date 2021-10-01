import React from "react";
import CountableItemService from "../../../../services/CountableItemService";
import data from "bootstrap/js/src/dom/data";
import {Button, Table} from "react-bootstrap";

class ViewAllCountableItems extends React.Component{
    constructor(props) {
        super(props);
        this.state = this.initialState

    }
    initialState={
        countableItems:[]
    }

    componentDidMount= async () => {
        await CountableItemService.getAllCountableItems()
            .then(response => response.data)
            .then((data) => {
                this.setState({countableItems:data});
            }).catch(error => {
                console.log("Cannot get all countable items. Error : ",error);
            })
    }

    render() {
        return (
            <div>
                <div>
                    <Table striped bordered hover variant={'light'}>
                        <thead>
                        <tr>
                            <td>Id</td>
                            <td>Name</td>
                            <td>Type</td>
                            <td>Quantity</td>
                            <td>Minimum Quantity</td>
                            <td>Site Id</td>
                            <td>Site Name</td>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.countableItems.length === 0?
                                <tr align={'center'}>
                                    <td colSpan={6}>{this.state.countableItems.length} records available</td>
                                </tr>:
                                this.state.countableItems.map((e) => (
                                    <tr key={e.id}>
                                        <td>{e.id}</td>
                                        <td>{e.name}</td>
                                        <td>{e.type}</td>
                                        <td>{e.quantity}</td>
                                        <td>{e.minimumQuantity}</td>
                                        <td>{e.siteid}</td>
                                        <td>{e.sitename}</td>

                                        <td>
                                            <Button className={'btn btn-warning'}>Edit</Button>
                                        </td>
                                    </tr>
                                ))
                        }
                        </tbody>
                    </Table>
                </div>

            </div>
        );
    }

}
export default ViewAllCountableItems;