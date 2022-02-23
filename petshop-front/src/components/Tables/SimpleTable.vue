<template>
  <div style="min-height: 30vh;">
    <md-empty-state v-if="empty" :md-size="20" md-description="Nothing in There" />
    <md-table v-model="data" :table-header-color="tableHeaderColor" @md-selected="onSelect">
      <md-table-row slot="md-table-row" slot-scope="{ item }" md-selectable="multiple" md-auto-select>
        <md-table-cell v-for="prop in props" :key="prop.key" :md-label="prop.label">{{ prop.formatter ? prop.formatter(item[prop.key]) : item[prop.key] }}</md-table-cell>
      </md-table-row>
    </md-table>
  </div>
</template>

<script>
import _ from 'lodash';
export default {
  name: "simple-table",
  props: {
    list: {
      type: Array,
      default: () => []
    },
    selected: {
      type: Array,
      default: () => []
    },
    props: {
      type: Array,
      default: () => []
    },
    tableHeaderColor: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      empty: false,
      selectedList: []
    }
  },
  computed: {
    data() {
      return this.list
    }
  },
  watch: {
    data(v) {
      if (_.isEmpty(v)) {
        this.empty = true
      }
    }
  },
  methods: {
    onSelect(items) {
      this.selectedList = items
      this.$emit('update:selected', items)
    }
  }
};
</script>
