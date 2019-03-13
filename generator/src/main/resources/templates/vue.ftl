<#assign dollar="$"/>
<#assign count=0/>
<#list table.fields as column>
    <#assign count=column_index/>
    <#if column.name = "parent_id">
        <#assign isTree=true/>
    </#if>
</#list>
<#assign lowerEntity = entity?uncap_first/>
<#if isTree??>
<template>
    <basic-container>
        <div class="deyatech-container pull-auto">
            <div class="deyatech-menu">
                <div class="deyatech-menu_left">
                    <el-button v-if="btnEnable.create" type="primary" :size="btnSize" @click="btnCreate" :disabled="selectedRows.length > 1">{{${dollar}t('table.create')}}</el-button>
                    <el-button v-if="btnEnable.update" type="primary" :size="btnSize" @click="btnUpdate" :disabled="selectedRows.length != 1">{{${dollar}t('table.update')}}</el-button>
                    <el-button v-if="btnEnable.delete" type="danger" :size="btnSize" @click="btnDelete" :disabled="selectedRows.length < 1">{{${dollar}t('table.delete')}}</el-button>
                </div>
                <div class="deyatech-menu_right">
                    <!--<el-button type="primary" icon="el-icon-edit" :size="btnSize" circle @click="btnUpdate"></el-button>
                    <el-button type="danger" icon="el-icon-delete" :size="btnSize" circle @click="btnDelete"></el-button>-->
                    <el-button icon="el-icon-refresh" :size="btnSize" circle @click="reloadList"></el-button>
                </div>
            </div>
        </div>

        <el-table :data="${lowerEntity}List" v-loading.body="listLoading" stripe border highlight-current-row
                  @selection-change="handleSelectionChange" v-if="tableReset">
            <el-table-column type="selection" width="50" align="center"/>
    <#list table.fields as column>
        <#if column.propertyName = "name">
            <el-table-tree-column fixed :expand-all="false" child-key="children" levelKey="level" :indent-size="20"
                                  parentKey="parentId" prop="${column.propertyName}" label="${column.comment}" width="200">
                <template slot-scope="scope">
                    <span class="link-type" @click='btnUpdate(scope.row)'>{{scope.row.name}}</span>
                </template>
            </el-table-tree-column>
        <#else>
            <el-table-column align="center" label="${column.comment}" prop="${column.propertyName}"/>
        </#if>
    </#list>
            <el-table-column prop="enable" :label="${dollar}t('table.enable')" align="center" width="90">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.enable | enums('EnableEnum') | statusFilter">
                        {{scope.row.enable | enums('EnableEnum')}}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="enable" class-name="status-col" :label="${dollar}t('table.operation')" align="center" width="150">
                <template slot-scope="scope">
                    <el-button v-if="btnEnable.create" :title="${dollar}t('table.create')" type="primary" icon="el-icon-plus" :size="btnSize" circle
                               @click.stop.safe="btnCreate(scope.row)"></el-button>
                    <el-button v-if="btnEnable.update" :title="${dollar}t('table.update')" type="primary" icon="el-icon-edit" :size="btnSize" circle
                               @click.stop.safe="btnUpdate(scope.row)"></el-button>
                    <el-button v-if="btnEnable.delete" :title="${dollar}t('table.delete')" type="danger" icon="el-icon-delete" :size="btnSize" circle
                               @click.stop.safe="btnDelete(scope.row)"></el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog :title="titleMap[dialogTitle]" :visible.sync="dialogVisible"
                   :close-on-click-modal="closeOnClickModal">
            <el-form ref="${lowerEntity}DialogForm" class="deyatech-form" :model="${lowerEntity}" label-position="right"
                     label-width="80px" :rules="${lowerEntity}Rules">
                <el-row :gutter="20" :span="24">
                    <el-col :span="12">
                        <el-form-item :label="${dollar}t('table.parent')">
                            <el-cascader :options="${lowerEntity}Cascader" v-model="${lowerEntity}TreePosition"
                                         show-all-levels expand-trigger="click" clearable
                                         change-on-select></el-cascader>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item :label="${dollar}t('table.searchName')" prop="name">
                            <el-input v-model="${lowerEntity}.name"/>
                        </el-form-item>
                    </el-col>
                </el-row>

<#list table.fields as column>
    <#if column.propertyName != "name" && column.propertyName != "parentId">
        <#if count % 2 != 0>
            <#if column_index % 2 == 0>
                <el-row :gutter="20" :span="24">
                    <el-col :span="12">
                        <el-form-item label="${column.comment}" prop="${column.propertyName}">
                            <el-input v-model="${lowerEntity}.${column.propertyName}"></el-input>
                        </el-form-item>
                    </el-col>
            <#else>
                    <el-col :span="12">
                        <el-form-item label="${column.comment}" prop="${column.propertyName}">
                            <el-input v-model="${lowerEntity}.${column.propertyName}"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </#if>
        <#else>
            <#if column_index % 2 == 0>
                <el-row :gutter="20" :span="24">
                    <el-col :span="12">
                        <el-form-item label="${column.comment}" prop="${column.propertyName}">
                            <el-input v-model="${lowerEntity}.${column.propertyName}"></el-input>
                        </el-form-item>
                    </el-col>
                <#if count == column_index>
                </el-row>
                </#if>
            <#else>
                    <el-col :span="12">
                        <el-form-item label="${column.comment}" prop="${column.propertyName}">
                            <el-input v-model="${lowerEntity}.${column.propertyName}"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </#if>
        </#if>
    </#if>
</#list>
                <el-row :gutter="20" :span="24">
                    <el-col :span="24">
                        <el-form-item :label="${dollar}t('table.remark')">
                            <el-input type="textarea" v-model="${lowerEntity}.remark" :rows="3"/>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button v-if="dialogTitle=='create'" type="primary" :size="btnSize" @click="doCreate" :loading="submitLoading">{{${dollar}t('table.confirm')}}</el-button>
                <el-button v-else type="primary" :size="btnSize" @click="doUpdate" :loading="submitLoading">{{${dollar}t('table.confirm')}}</el-button>
                <el-button :size="btnSize" @click="dialogVisible = false">{{${dollar}t('table.cancel')}}</el-button>
            </span>
        </el-dialog>
    </basic-container>
</template>

<script>
    import {
        get${entity}Tree,
        get${entity}Cascader,
        createOrUpdate${entity},
        del${entity}s} from '@/api/${package.ModuleName}/${lowerEntity}';
    import {deepClone, setExpanded} from '@/util/util';
    import {mapGetters} from 'vuex';

    export default {
        name: '${lowerEntity}',
        data() {
            return {
                ${lowerEntity}List: undefined,
                listLoading: true,
                ${lowerEntity}: {
                    id: undefined,
                <#list table.fields as column>
                    ${column.propertyName}: undefined<#if count != column_index>,</#if>
                </#list>
                },
                ${lowerEntity}Cascader: [],
                dialogVisible: false,
                dialogTitle: undefined,
                submitLoading: false,
                selectedRows: [],
                ${lowerEntity}Rules: {
                <#list table.fields as column>
                    ${column.propertyName}: [
                        {required: true, message: this.${dollar}t("table.pleaseInput") + '${column.comment}'}
                    ]<#if count != column_index>,</#if>
                </#list>
                },
                lastExpanded: undefined,
                tableReset: false
            }
        },
        created() {
            this.reloadList();
        },
        computed: {
            ${lowerEntity}TreePosition: {
                get() {
                    if (this.${lowerEntity}.treePosition) {
                        return this.${lowerEntity}.treePosition.split('&');
                    }
                },
                set(v) {
                    if (v.length > 0) {
                        this.${lowerEntity}.parentId = v[v.length - 1];
                        this.${lowerEntity}.treePosition = v.join('&') + "&" + this.${lowerEntity}.parentId;
                    } else {
                        this.${lowerEntity}.parentId = 0;
                        this.${lowerEntity}.treePosition = undefined;
                    }
                }
            },
            ...mapGetters([
                'permission',
                'titleMap',
                'enums',
                'closeOnClickModal',
                'searchSize',
                'btnSize'
            ]),
            btnEnable() {
                return {
                    create: this.permission.${lowerEntity}_create,
                    update: this.permission.${lowerEntity}_update,
                    delete: this.permission.${lowerEntity}_delete
                };
            }
        },
        methods: {
            reloadList(){
                this.listLoading = true;
                get${entity}Tree().then(response => {
                    this.tableReset = false;
                    this.${lowerEntity}List = response.data;
                    if (this.lastExpanded) {
                        this.${lowerEntity}List = setExpanded(this.${lowerEntity}List, this.lastExpanded);
                    }
                    this.$nextTick(() => {
                        this.tableReset = true
                    });
                    this.listLoading = false;
                })
            },
            get${entity}Cascader(id){
                this.submitLoading = true;
                get${entity}Cascader(id).then(response => {
                    this.submitLoading = false;
                    this.${lowerEntity}Cascader = response.data;
                })
            },
            handleSelectionChange(rows){
                this.selectedRows = rows;
            },
            btnCreate(row){
                this.reset${entity}();
                if (row.id) {
                    if(row.treePosition != null){
                        this.${lowerEntity}.treePosition = row.treePosition + "&" + row.id;
                    }else{
                        this.${lowerEntity}.treePosition = "&" + row.id;
                    }
                    this.${lowerEntity}.parentId = row.id;
                } else {
                    if (this.selectedRows.length == 1) {
                        if(this.selectedRows[0].treePosition != null){
                            this.${lowerEntity}.treePosition = this.selectedRows[0].treePosition + "&" + this.selectedRows[0].id;
                        }else{
                            this.${lowerEntity}.treePosition = "&" + this.selectedRows[0].id;
                        }
                        this.${lowerEntity}.parentId = this.selectedRows[0].id;
                    }
                }
                this.${lowerEntity}.children = undefined;
                this.get${entity}Cascader(null);
                this.dialogTitle = 'create';
                this.dialogVisible = true;
            },
            btnUpdate(row){
                this.reset${entity}();
                if (row.id) {
                    this.${lowerEntity} = deepClone(row);
                } else {
                    this.${lowerEntity} = deepClone(this.selectedRows[0]);
                }
                this.${lowerEntity}.children = undefined;
                this.get${entity}Cascader(this.${lowerEntity}.id);
                this.dialogTitle = 'update';
                this.dialogVisible = true;
            },
            btnDelete(row){
                let ids = [];
                if (row.id) {
                    this.${dollar}confirm(this.${dollar}t("table.deleteConfirm"), this.${dollar}t("table.tip"), {type: 'error'}).then(() => {
                        ids.push(row.id);
                        this.lastExpanded = row.treePosition;
                        this.doDelete(ids);
                    })
                } else {
                    this.${dollar}confirm(this.${dollar}t("table.deleteConfirm"), this.${dollar}t("table.tip"), {type: 'error'}).then(() => {
                        for(const deleteRow of this.selectedRows) {
                            this.lastExpanded = deleteRow.treePosition;
                            ids.push(deleteRow.id);
                        }
                        this.doDelete(ids);
                    })
                }
            },
            doCreate(){
                this.${dollar}refs['${lowerEntity}DialogForm'].validate(valid => {
                    if(valid) {
                        this.submitLoading = true;
                        createOrUpdate${entity}(this.${lowerEntity}).then(response => {
                            this.lastExpanded = this.${lowerEntity}.treePosition;
                            this.reset${entity}Dialog();
                            this.${dollar}message.success(this.${dollar}t("table.createSuccess"));
                        })
                    } else {
                        return false;
                    }
                })
            },
            doUpdate(){
                this.${dollar}refs['${lowerEntity}DialogForm'].validate(valid => {
                    if(valid) {
                        this.submitLoading = true;
                        createOrUpdate${entity}(this.${lowerEntity}).then(response => {
                            this.lastExpanded = this.${lowerEntity}.treePosition;
                            this.reset${entity}Dialog();
                            this.${dollar}message.success(this.${dollar}t("table.updateSuccess"));
                        })
                    } else {
                        return false;
                    }
                })
            },
            doDelete(ids){
                this.listLoading = true;
                del${entity}s(ids).then(response => {
                    this.reloadList();
                    this.${dollar}message.success(this.${dollar}t("table.deleteSuccess"));
                })
            },
            reset${entity}(){
                this.${lowerEntity} = {
                    id: undefined,
                <#list table.fields as column>
                    ${column.propertyName}: undefined<#if count != column_index>,</#if>
                </#list>
                }
            },
            reset${entity}Dialog(){
                this.dialogVisible = false;
                this.reset${entity}();
                this.reloadList();
                this.submitLoading = false;
            }
        }
    }
</script>
<#else>
<template>
    <basic-container>
        <div class="deyatech-container pull-auto">
            <div class="deyatech-header">
                <el-form :inline="true" ref="searchForm">
                    <el-form-item>
                        <el-input :size="searchSize" :placeholder="${dollar}t('table.searchName')" v-model="listQuery.name"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" icon="el-icon-search" :size="searchSize" @click="reloadList">{{${dollar}t('table.search')}}</el-button>
                        <el-button icon="el-icon-delete" :size="searchSize" @click="resetSearch">{{${dollar}t('table.clear')}}</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <div class="deyatech-menu">
                <div class="deyatech-menu_left">
                    <el-button v-if="btnEnable.create" type="primary" :size="btnSize" @click="btnCreate">{{${dollar}t('table.create')}}</el-button>
                    <el-button v-if="btnEnable.update" type="primary" :size="btnSize" @click="btnUpdate" :disabled="selectedRows.length != 1">{{${dollar}t('table.update')}}</el-button>
                    <el-button v-if="btnEnable.delete" type="danger" :size="btnSize" @click="btnDelete" :disabled="selectedRows.length < 1">{{${dollar}t('table.delete')}}</el-button>
                </div>
                <div class="deyatech-menu_right">
                    <!--<el-button type="primary" icon="el-icon-edit" :size="btnSize" circle @click="btnUpdate"></el-button>
                    <el-button type="danger" icon="el-icon-delete" :size="btnSize" circle @click="btnDelete"></el-button>-->
                    <el-button icon="el-icon-refresh" :size="btnSize" circle @click="reloadList"></el-button>
                </div>
            </div>
            <el-table :data="${lowerEntity}List" v-loading.body="listLoading" stripe border highlight-current-row
                      @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="50" align="center"/>
        <#list table.fields as column>
            <#if column.propertyName = "name">
                <el-table-column align="center" label="${column.comment}" prop="${column.propertyName}">
                    <template slot-scope="scope">
                        <span class="link-type" @click='btnUpdate(scope.row)'>{{scope.row.name}}</span>
                    </template>
                </el-table-column>
            <#else>
                <el-table-column align="center" label="${column.comment}" prop="${column.propertyName}"/>
            </#if>
        </#list>
                <el-table-column prop="enable" :label="${dollar}t('table.enable')" align="center" width="90">
                    <template slot-scope="scope">
                        <el-tag :type="scope.row.enable | enums('EnableEnum') | statusFilter">
                            {{scope.row.enable | enums('EnableEnum')}}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="enable" class-name="status-col" :label="${dollar}t('table.operation')" align="center" width="100">
                    <template slot-scope="scope">
                        <el-button v-if="btnEnable.update" :title="${dollar}t('table.update')" type="primary" icon="el-icon-edit" :size="btnSize" circle
                                   @click.stop.safe="btnUpdate(scope.row)"></el-button>
                        <el-button v-if="btnEnable.delete" :title="${dollar}t('table.delete')" type="danger" icon="el-icon-delete" :size="btnSize" circle
                                   @click.stop.safe="btnDelete(scope.row)"></el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination class="deyatech-pagination pull-right" background
                           :current-page.sync="listQuery.page" :page-sizes="this.$store.state.common.pageSize"
                           :page-size="listQuery.rows" :layout="this.$store.state.common.pageLayout" :total="total"
                           @size-change="handleSizeChange" @current-change="handleCurrentChange">
            </el-pagination>


            <el-dialog :title="titleMap[dialogTitle]" :visible.sync="dialogVisible"
                       :close-on-click-modal="closeOnClickModal">
                <el-form ref="${lowerEntity}DialogForm" class="deyatech-form" :model="${lowerEntity}" label-position="right"
                         label-width="80px" :rules="${lowerEntity}Rules">
        <#list table.fields as column>
            <#if count % 2 != 0>
                <#if column_index % 2 == 0>
                    <el-row :gutter="20" :span="24">
                        <el-col :span="12">
                            <el-form-item label="${column.comment}" prop="${column.propertyName}">
                                <el-input v-model="${lowerEntity}.${column.propertyName}"></el-input>
                            </el-form-item>
                        </el-col>
                <#else>
                        <el-col :span="12">
                            <el-form-item label="${column.comment}" prop="${column.propertyName}">
                                <el-input v-model="${lowerEntity}.${column.propertyName}"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </#if>
            <#else>
                <#if column_index % 2 == 0>
                    <el-row :gutter="20" :span="24">
                        <el-col :span="12">
                            <el-form-item label="${column.comment}" prop="${column.propertyName}">
                                <el-input v-model="${lowerEntity}.${column.propertyName}"></el-input>
                            </el-form-item>
                        </el-col>
                    <#if count == column_index>
                    </el-row>
                    </#if>
                <#else>
                        <el-col :span="12">
                            <el-form-item label="${column.comment}" prop="${column.propertyName}">
                                <el-input v-model="${lowerEntity}.${column.propertyName}"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </#if>
            </#if>
        </#list>
                    <el-row :gutter="20" :span="24">
                        <el-col :span="24">
                            <el-form-item :label="${dollar}t('table.remark')">
                                <el-input type="textarea" v-model="${lowerEntity}.remark" :rows="3"/>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button v-if="dialogTitle=='create'" type="primary" :size="btnSize" @click="doCreate" :loading="submitLoading">{{${dollar}t('table.confirm')}}</el-button>
                    <el-button v-else type="primary" :size="btnSize" @click="doUpdate" :loading="submitLoading">{{${dollar}t('table.confirm')}}</el-button>
                    <el-button :size="btnSize" @click="dialogVisible = false">{{${dollar}t('table.cancel')}}</el-button>
                </span>
            </el-dialog>
        </div>
    </basic-container>
</template>


<script>
    import {mapGetters} from 'vuex';
    import {deepClone} from '@/util/util';
    import {
        get${entity}List,
        createOrUpdate${entity},
        del${entity}s
    } from '@/api/${package.ModuleName}/${lowerEntity}';

    export default {
        name: '${lowerEntity}',
        data() {
            return {
                ${lowerEntity}List: undefined,
                total: undefined,
                listLoading: true,
                listQuery: {
                    page: this.$store.state.common.page,
                    rows: this.$store.state.common.rows,
                    name: undefined
                },
                ${lowerEntity}: {
                    id: undefined,
                <#list table.fields as column>
                    ${column.propertyName}: undefined<#if count != column_index>,</#if>
                </#list>
                },
                ${lowerEntity}Rules: {
                <#list table.fields as column>
                    ${column.propertyName}: [
                        {required: true, message: this.${dollar}t("table.pleaseInput") + '${column.comment}'}
                    ]<#if count != column_index>,</#if>
                </#list>
                },
                selectedRows: [],
                dialogVisible: false,
                dialogTitle: undefined,
                submitLoading: false
            }
        },
        computed: {
            ...mapGetters([
                'permission',
                'titleMap',
                'enums',
                'closeOnClickModal',
                'searchSize',
                'btnSize'
            ]),
            btnEnable() {
                return {
                    create: this.permission.${lowerEntity}_create,
                    update: this.permission.${lowerEntity}_update,
                    delete: this.permission.${lowerEntity}_delete
                };
            }
        },
        created(){
            this.reloadList();
        },
        methods: {
            resetSearch(){
                this.listQuery.name = undefined;
            },
            reloadList(){
                this.listLoading = true;
                this.${lowerEntity}List = undefined;
                this.total = undefined;
                get${entity}List(this.listQuery).then(response => {
                    this.listLoading = false;
                    this.${lowerEntity}List = response.data.records;
                    this.total = response.data.total;
                })
            },
            handleSizeChange(val){
                this.listQuery.rows = val;
                this.reloadList();
            },
            handleCurrentChange(val){
                this.listQuery.page = val;
                this.reloadList();
            },
            handleSelectionChange(rows){
                this.selectedRows = rows;
            },
            btnCreate(){
                this.reset${entity}();
                this.dialogTitle = 'create';
                this.dialogVisible = true;
            },
            btnUpdate(row){
                this.reset${entity}();
                if (row.id) {
                    this.${lowerEntity} = deepClone(row);
                } else {
                    this.${lowerEntity} = deepClone(this.selectedRows[0]);
                }
                this.dialogTitle = 'update';
                this.dialogVisible = true;
            },
            btnDelete(row){
                let ids = [];
                if (row.id) {
                    this.${dollar}confirm(this.${dollar}t("table.deleteConfirm"), this.${dollar}t("table.tip"), {type: 'error'}).then(() => {
                        ids.push(row.id);
                        this.doDelete(ids);
                    })
                } else {
                    this.${dollar}confirm(this.${dollar}t("table.deleteConfirm"), this.${dollar}t("table.tip"), {type: 'error'}).then(() => {
                        for(const deleteRow of this.selectedRows){
                            ids.push(deleteRow.id);
                        }
                        this.doDelete(ids);
                    })
                }
            },
            doCreate(){
                this.${dollar}refs['${lowerEntity}DialogForm'].validate(valid => {
                    if(valid) {
                        this.submitLoading = true;
                        createOrUpdate${entity}(this.${lowerEntity}).then(response => {
                            this.reset${entity}Dialog();
                            this.${dollar}message.success(this.${dollar}t("table.createSuccess"));
                        })
                    } else {
                        return false;
                    }
                });
            },
            doUpdate(){
                this.${dollar}refs['${lowerEntity}DialogForm'].validate(valid => {
                    if(valid) {
                        this.submitLoading = true;
                        createOrUpdate${entity}(this.${lowerEntity}).then(response => {
                            this.reset${entity}Dialog();
                            this.${dollar}message.success(this.${dollar}t("table.updateSuccess"));
                        })
                    } else {
                        return false;
                    }
                })
            },
            doDelete(ids){
                this.listLoading = true;
                del${entity}s(ids).then(response => {
                    this.reloadList();
                    this.${dollar}message.success(this.${dollar}t("table.deleteSuccess"));
                })
            },
            reset${entity}(){
                this.${lowerEntity} = {
                    id: undefined,
                <#list table.fields as column>
                    ${column.propertyName}: undefined<#if count != column_index>,</#if>
                </#list>
                }
            },
            reset${entity}Dialog(){
                this.dialogVisible = false;
                this.submitLoading = false;
                this.reset${entity}();
                this.reloadList();
            }
        }
    }
</script>
</#if>


